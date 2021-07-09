package ru.job4j.concurrent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(50);
    private final int size = Runtime.getRuntime().availableProcessors();

    public void work(Runnable job) {

    }

    public void shutdown() {

    }
}
