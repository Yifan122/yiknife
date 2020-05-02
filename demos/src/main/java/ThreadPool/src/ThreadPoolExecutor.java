package ThreadPool.src;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class ThreadPoolExecutor {
    private final int nTreads;
    private final BlockingDeque<Runnable> queue;
    private final Worker[] workers;

    public ThreadPoolExecutor(int numberThread) {
        this.nTreads = numberThread;
        queue = new LinkedBlockingDeque<>();
        workers = new Worker[numberThread];
        for (int i = 0; i < numberThread; i++) {
            workers[i] = new Worker();
            workers[i].start();
        }
    }

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5);
        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.submit(new Task(i));
        }
    }

    public void submit(Runnable task) {
        synchronized (queue) {
            if (queue.offer(task)) {
                queue.notifyAll();
            } else {
                System.out.println("Task queue is full, discard the task");
            }
        }
    }

    public static class Task implements Runnable {

        private int num;

        public Task(int n) {
            num = n;
        }

        public void run() {
            try {
                System.out.printf("%s for %d: started%n", Thread.currentThread().getName(), num);
                Thread.sleep(1000 * 3);
                System.out.printf("%s for %d: finished%n", Thread.currentThread().getName(), num);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            Runnable task;

            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    task = queue.poll();
                }

                task.run();
            }
        }
    }
}
