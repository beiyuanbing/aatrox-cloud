package com.aatrox.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/2
 */
public class ThreadCachePool {
    private static Logger logger = LoggerFactory.getLogger(ThreadCachePool.class);
    private static ExecutorService threadCachePool = null;

    public ThreadCachePool() {
    }

    private static synchronized ExecutorService getPool() {
        if (threadCachePool == null) {
            threadCachePool = Executors.newCachedThreadPool();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                shutdownNow();
            }));
        }

        return threadCachePool;
    }

    public static <V> Future<V> call(LocalCallable<V> task) {
        return getPool().submit(task);
    }

    public static void execute(LocalRunnable task) {
        getPool().execute(task);
    }

    public static Future<?> submit(LocalRunnable task) {
        return getPool().submit(task);
    }

    public static CompletableFuture<Void> runAsync(LocalRunnable task) {
        return CompletableFuture.runAsync(task, getPool());
    }

    public static <V> CompletableFuture<V> supplyAsync(LocalCallable<V> task) {
        return CompletableFuture.supplyAsync(() -> {
            return task.call();
        }, getPool());
    }

    private static void shutdown() {
        threadCachePool.shutdown();
        logger.info("ThreadCachePool shutdowning!");
    }

    private static void shutdownNow() {
        threadCachePool.shutdownNow();
        logger.info("ThreadCachePool shutdown!");
    }

    public static String poolInfo() {
        return threadCachePool.toString();
    }

    public static int poolTaskCount() {
        ThreadPoolExecutor tp = (ThreadPoolExecutor)threadCachePool;
        return tp.getQueue().size() + tp.getActiveCount();
    }
}
