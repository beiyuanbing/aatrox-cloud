package com.aatrox.common.excel.export;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */

import com.aatrox.common.excel.base.DataLoader;
import com.aatrox.common.excel.support.AbstractDataProvider;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * 并发的数据加载器，内部维护一个线程池，总共50个线程（默认）负责数据加载
 * 采用并发方式导出的 Excel 不能保证数据的有序性，例如某个导出任务需要导出20w数据，采用分页数据加载，每页加载1000条数据，总共需要查询200页
 * 因为内部使用多线程方式加载数据，总共50个线程，所以1-50页的数据会同时进行加载，而这50个线程哪个线程先查询完成并返回是无法保证的
 * 所以整个Excel的数据顺序是无法保证的，只能保证各分页内数据的有序（如果SQL有设置排序）
 * 当某个线程查询完成之后，会将数据放到 {@link ConcurrentLargeExportDataProvider#dataQueue} 中，然后继续请求下一页未被加载的页数据，直到所有页数据被加载完成
 * 放入队列中的数据会被立刻写入 Excel 中，写 Excel 的线程会循环队列中的数据，然后一条条写 Excel，每写完一条，该条数据就会被释放（JVM可回收这条数据，所以即使加载大量数据也不会导致内存溢出）
 *
 * 这里暂时采用固定大小线程池，总共50个线程参与数据加载
 * 注：这里的线程池是 static 的，即所有采用 {@link ConcurrentLargeExcelExportDocument} 方式导出 Excel 都共享这个线程池
 * 会存在的问题：
 * 例如如果导出 Excel 任务A，可能导出的数据量非常大，因为线程池中最大的数据加载线程只有50个，50页之后的数据加载需要在队列中排队
 * 如果此时发起了另外一个导出 Excel 任务B，因为此时线程池已经没有空闲线程来加载数据，所以B加载数据的任务也只能排队等待，
 * 所以即使B导出的数据量很少，也有可能等待非常长的时间（需要和前一个未完成的任务竞争数据加载权限）
 *
 * 关于为什么将线程池设置为 static，而不是每一个导出任务创建一个私有的线程池？
 * 1）线程的创建和销毁本身很耗性能
 * 2）最主要的还是因为如果每个导出任务创建一个私有线程池的话，如果某个时刻存在大量的导出任务，那么这个导出功能可能瞬间将JVM进程的可用线程全部占用完
 * 3）每个任务私有线程池的话，那么加载数据的线程数量很容易就超出系统设置的 JDBC 线程池数量，如果超出将导致其他系统其他功能无法获取到 JDBC 连接，导致其他功能无法使用
 * 综上，所以这里的使用共享的线程池是个更好的选择
 *
 * 关于为什么使用固定大小的线程池，有没有更好的选择？
 * 因为数据加载的任务属于I/O密集型的任务，所以线程数量可以设置稍微大一点（比CPU的核心数量）
 * 1）{@link Executors#newCachedThreadPool()} 不使用这种自动收缩的线程池，是因为这种线程池，没有缓冲队列（SynchronousQueue 不会缓存任务），
 * 当导出数据量大时会创建过多的线程数量，即创建的最大线程数量不好控制（设置过小会导致任务拒绝），好处是这种线程池可以自动收缩
 * 2）{@link Executors#newFixedThreadPool(int)} 内部有队列，最大线程数量可控制，超出最大线程数量的任务暂存在队列中，依次处理；缺点时线程数量不会自动收缩
 * 3）最好的做法是既可以像 {@link Executors#newCachedThreadPool()} 一样会自动收缩，又可以像 {@link Executors#newFixedThreadPool(int)} 一样控制最大线程数量，并缓存任务到队列
 * 只能自己构建 {@link ThreadPoolExecutor} 对象，但是 {@link ThreadPoolExecutor} 是按 corePoolSize 进行收缩的，所以在同时设置了 corePoolSize 和 queue 的时候 maximumPoolSize 不会首先取作用
 *
 * @since 1.0
 *
 * @see LargeExportDataProvider
 */
public class ConcurrentLargeExportDataProvider<T> extends AbstractDataProvider<T> {

    private static final Logger LOG = Logger.getLogger(ConcurrentLargeExportDataProvider.class.getName());
    // 最大20个线程同时加载数据，线程数量越多加载速度理论上更快，但是内存压力也越大
    private static final int MAX_MUM_POOL_SIZE = 30;

    private final static ExecutorService executorService = Executors.newFixedThreadPool(MAX_MUM_POOL_SIZE);

    // 数据分页加载器
    private final DataLoader<T> dataLoader;

    // 分页查询时，每页查询的记录数
    private final int pageSize;

    // 数据缓冲队列
    private final Queue<T> dataQueue;

    // 存活的任务数量，只有当所有的数据加载任务都退出时，整个数据加载才算完成
    private AtomicInteger liveTaskSize;

    public ConcurrentLargeExportDataProvider(DataLoader<T> dataLoader) {
        this(dataLoader, PageHelper.DEFAULT_PAGE_SIZE);
    }

    public ConcurrentLargeExportDataProvider(DataLoader<T> dataLoader, int pageSize) {
        this.dataLoader = dataLoader;
        this.pageSize = pageSize;
        this.dataQueue = new ConcurrentLinkedQueue<T>();
    }

    public final void loadDatas() {
        PageHelper page = this.initPage();
        int totalPages = page.getTotalPages();
        this.liveTaskSize = new AtomicInteger(totalPages);

        for(int i = 0; i < totalPages; i++) {
            executorService.execute(() -> {
                try {
                    long start = System.currentTimeMillis();

                    int pageNum = page.getNextPageNum();
                    Collection<T> datas = dataLoader.load(pageNum, pageSize);
                    dataQueue.addAll(datas);

                    long end = System.currentTimeMillis();
                    LOG.info(new StringBuilder().append("load date in page number: ")
                            .append(pageNum).append(", page data size: ").append(datas.size())
                            .append(", use total time: ").append(end - start).append("ms")
                            .append(", current thread: ").append(Thread.currentThread().getName()).toString());
                } finally {
                    int lt = liveTaskSize.decrementAndGet();
                    LOG.info("current live task size : " + lt);
                }
            });
        }
    }

    public T next() {
        return dataQueue.poll();
    }

    public boolean isLast() {
        return liveTaskSize.get() == 0 && dataQueue.isEmpty();
    }

    @Override
    public Collection<T> getDatas() {
        throw new UnsupportedOperationException("");
    }

    @Override
    public void fillDatas(Collection<?> datas) {
        throw new UnsupportedOperationException("");
    }

    private PageHelper initPage() {
        int totalRecord = dataLoader.selectTotalCount();
        return new PageHelper(totalRecord, this.pageSize);
    }

}