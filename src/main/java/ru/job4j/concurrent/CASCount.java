package ru.job4j.concurrent;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        int tmp;
        do {
            tmp = count.get();
        } while (!count.compareAndSet(tmp, ++tmp));
    }

    public int get() {
        int tmp;
        do {
            tmp = count.get();
        } while (!count.compareAndSet(tmp, tmp));
        return tmp;
    }
}

