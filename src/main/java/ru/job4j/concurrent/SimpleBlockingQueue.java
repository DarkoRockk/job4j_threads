package ru.job4j.concurrent;

import net.jcip.annotations.*;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int limit = 5;

    public synchronized void offer(T value) {
        if (limit < queue.size()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        queue.add(value);
        notify();

    }

    public synchronized T poll() throws InterruptedException {
        if (queue.isEmpty()) {
            wait();
        }
        T rsl = queue.poll();
        notify();
        return rsl;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
