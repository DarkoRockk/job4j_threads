package ru.job4j.concurrent;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class SimpleBlockingQueueTest {

    @Test
    public void whenAllRight() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    for (int i = 1; i < 10; i++) {
                        queue.offer(i);
                    }
                },
                "Producer"
        );
        Thread consumer = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    for (int i = 1; i < 7; i++) {
                        try {
                            queue.poll();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                },
                "Consumer"
        );
        producer.start();
        producer.join();
        consumer.start();
        consumer.join();
        assertThat(queue.getSize(), is(3));
    }
}