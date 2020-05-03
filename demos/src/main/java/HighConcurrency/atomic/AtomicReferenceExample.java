package HighConcurrency.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceExample {
    private static AtomicReference<Integer> atomicReference = new AtomicReference<>(0);

    public static void main(String[] args) {
        atomicReference.compareAndSet(0, 2); // 2
        atomicReference.compareAndSet(0, 1); // no
        atomicReference.compareAndSet(1, 3); // no
        atomicReference.compareAndSet(2, 4); // 4
        atomicReference.compareAndSet(4, 5); // 5
        atomicReference.compareAndSet(3, 6); // no
        System.out.println(atomicReference.get());
    }
}
