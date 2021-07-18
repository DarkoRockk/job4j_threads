package ru.job4j.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T object;
    private int start;
    private int finish;

    public ParallelIndexSearch(T[] array, T object, int start, int finish) {
        this.array = array;
        this.object = object;
        this.start = start;
        this.finish = finish;
    }

    public int checkArray(T[] array, T object) {
        int rsl = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(object)) {
                rsl = i;
            }
        }
        return rsl;
    }

    @Override
    protected Integer compute() {
        if ((finish - start) <= 10) {
            return checkArray(array, object);
        }
        int middle = (start + finish) / 2;
        ParallelIndexSearch<T> leftHalf
                = new ParallelIndexSearch<>(array, object, start, middle);
        ParallelIndexSearch<T> rightHalf
                = new ParallelIndexSearch<>(array, object, middle + 1, finish);

        leftHalf.fork();
        rightHalf.fork();
        int a = leftHalf.join();
        int b = rightHalf.join();
        return a != -1 ? a : b;
    }

    public static <T> Integer findIndex(T[] array, T object) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelIndexSearch<>(array, object, 0, array.length - 1));
    }

    public static void main(String[] args) {
        User[] users = new User[100];
        for (int i = 0; i < users.length; i++) {
            users[i] = User.of("name" + i);
        }

        User test = users[67];
        System.out.println(users[5].equals(test));
        System.out.println(findIndex(users, test));
    }
}
