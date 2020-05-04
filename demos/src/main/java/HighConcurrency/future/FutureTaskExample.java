package HighConcurrency.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class FutureTaskExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        Future<String> future = threadPool.submit(new CallableClass());

        System.out.println(future.get());
    }

    static class CallableClass implements Callable<String> {
        @Override
        public String call() throws Exception {
            log.info("Do something");
            Thread.sleep(1000);
            return "Done";
        }
    }
}
