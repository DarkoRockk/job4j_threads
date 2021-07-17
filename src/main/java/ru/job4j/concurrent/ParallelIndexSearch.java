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

    @Override
    protected Integer compute() {
        int rsl = -1;
        if ((finish - start) <= 10) {
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(object)) {
                    rsl = i;
                    return rsl;
                }
            }
        }
        int middle = start + ((finish - start) / 2);
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

        User test = User.of("name" + 67);
        System.out.println(findIndex(users, test));
    }
}
