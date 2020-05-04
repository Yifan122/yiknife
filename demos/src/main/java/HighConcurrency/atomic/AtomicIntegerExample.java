package HighConcurrency.atomic;

import HighConcurrency.annotation.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ThreadSafe
public class AtomicIntegerExample {
    private static AtomicInteger count = new AtomicInteger(0);

    private static void add() {
        count.incrementAndGet();
    }

    public static void main(String[] args) throws Exception {
        // 并发总数
        final int totalClient = 50000;
        //线程总数
        final int totalThreadNumber = 200;
        Semaphore semaphore = new Semaphore(totalThreadNumber);
        CountDownLatch countDownLatch = new CountDownLatch(totalClient);
        ExecutorService threadpool = Executors.newCachedThreadPool();
        for (int i = 0; i < totalClient; i++) {
            threadpool.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                } catch (Exception e) {
                    log.error("Exceptions in add: {}", e.toString());
                } finally {
                    semaphore.release();
                }
            });
            countDownLatch.countDown();
        }

        countDownLatch.await();
        threadpool.shutdown();
        System.out.println(count.get());
    }
}
