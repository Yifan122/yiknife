package HighConcurrency.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

@Slf4j
public class AtomicFieldUpdaterExample {
    private static AtomicIntegerFieldUpdater<AtomicFieldUpdaterExample> updater
            = AtomicIntegerFieldUpdater.newUpdater(AtomicFieldUpdaterExample.class, "count");
    private static AtomicFieldUpdaterExample example = new AtomicFieldUpdaterExample();
    private volatile int count = 100;

    public static void main(String[] args) {
        if (updater.compareAndSet(example, 100, 120)) {
            log.error("Log success");
        }

        if (updater.compareAndSet(example, 100, 120)) {
            log.error("Log success again");
        } else {
            log.error("Log fail");
        }
    }
}
