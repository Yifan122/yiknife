package HighConcurrency.simpleDateFormat;

import HighConcurrency.annotation.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
@ThreadSafe
public class ThreadLocalSimpleDateFormat {
    private static ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMdd"));

    private static void update() {
        try {
            simpleDateFormatThreadLocal.get().parse("20200403");
        } catch (ParseException e) {
            log.error("Parse error");
        }
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
                    update();
                    semaphore.release();
                } catch (Exception e) {
                    log.error("Exceptions in add: {}", e.toString());
                }
            });
            countDownLatch.countDown();
        }

        countDownLatch.await();
        threadpool.shutdown();
    }
}
