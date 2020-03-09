package com.aatrox.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/2
 */
public class ThreadFixedPool {
    private static Logger logger = LoggerFactory.getLogger(ThreadFixedPool.class);
    private static ConcurrentHashMap<String, ExecutorService> poolMap = new ConcurrentHashMap();

    public ThreadFixedPool() {
    }

    private static synchronized ExecutorService getPool(ThreadFixedPool.ThreadKey poolKey) {
        ExecutorService pool = (ExecutorService)poolMap.get(poolKey.getKey());
        if (pool == null) {
            pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            poolMap.put(poolKey.getKey(), pool);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                shutdownNow(poolKey);
            }));
        }

        return pool;
    }

    public static <V> Future<V> call(ThreadFixedPool.ThreadKey poolKey, LocalCallable<V> task) {
        return getPool(poolKey).submit(task);
    }

    public static void execute(ThreadFixedPool.ThreadKey poolKey, LocalRunnable task) {
        getPool(poolKey).execute(task);
    }

    public static Future<?> submit(ThreadFixedPool.ThreadKey poolKey, LocalRunnable task) {
        return getPool(poolKey).submit(task);
    }

    public static CompletableFuture<Void> runAsync(ThreadFixedPool.ThreadKey poolKey, LocalRunnable task) {
        return CompletableFuture.runAsync(task, getPool(poolKey));
    }

    public static <V> CompletableFuture<V> supplyAsync(ThreadFixedPool.ThreadKey poolKey, LocalCallable<V> task) {
        return CompletableFuture.supplyAsync(() -> {
            return task.call();
        }, getPool(poolKey));
    }

    private static void shutdown(ThreadFixedPool.ThreadKey poolKey) {
        ExecutorService pool = getPool(poolKey);
        if (pool != null) {
            pool.shutdown();
            logger.info("ThreadFixedPool shutdowning!");
        }

    }

    private static void shutdownNow(ThreadFixedPool.ThreadKey poolKey) {
        ExecutorService pool = getPool(poolKey);
        if (pool != null) {
            pool.shutdownNow();
            logger.info("ThreadFixedPool shutdown!");
        }

    }

    public static String poolInfo(ThreadFixedPool.ThreadKey poolKey) {
        ExecutorService pool = getPool(poolKey);
        return pool != null ? pool.toString() : "";
    }

    public static int poolTaskCount(ThreadFixedPool.ThreadKey poolKey) {
        ExecutorService pool = getPool(poolKey);
        if (pool != null) {
            ThreadPoolExecutor tp = (ThreadPoolExecutor)pool;
            return tp.getQueue().size() + tp.getActiveCount();
        } else {
            return 0;
        }
    }

    public interface ThreadKey {
        String getKey();
    }
}
