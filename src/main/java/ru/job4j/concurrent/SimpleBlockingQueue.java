package ru.job4j.concurrent;

import net.jcip.annotations.*;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    public synchronized int getSize() {
        return queue.size();
    }

    public synchronized void offer(T value) {
        try {
            queue.add(value);
            this.notify();
        } catch (IllegalStateException e) {
            try {
                this.wait();
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized T poll() {
        T rsl = queue.poll();
        while (rsl == null) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.notify();
        return rsl;
    }
}
