package ru.job4j.concurrent;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T object;

    public ParallelIndexSearch(T[] array, T object) {
        this.array = array;
        this.object = object;
    }

    @Override
    protected Integer compute() {
        if (array.length <= 10) {
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(object)) {
                    return i;
                }

            }
        }
        ParallelIndexSearch<T> leftHalf
                = new ParallelIndexSearch<>(Arrays.copyOfRange(array, 0, array.length / 2), object);
        ParallelIndexSearch<T> rightHalf
                = new ParallelIndexSearch<>(Arrays.copyOfRange(array, array.length / 2, array.length), object);

        leftHalf.fork();
        rightHalf.fork();
        int a = leftHalf.join();
        int b = rightHalf.join();
        return a != -1 ? a : b;
    }

    public static <T> Integer findIndex(T[] array, T object) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelIndexSearch<>(array, object));
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
