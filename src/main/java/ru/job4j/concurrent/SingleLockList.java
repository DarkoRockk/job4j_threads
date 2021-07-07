package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {

    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = Collections.synchronizedList(list);
    }

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }

    public synchronized List<T> copy(List<T> list) {
        return List.copyOf(list);
    }


    @Override
    public Iterator<T> iterator() {
        return copy(this.list).iterator();
    }
}