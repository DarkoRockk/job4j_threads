package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

public class Wget2 implements Runnable {
    private final String url;
    private final int speed;

    public Wget2(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("temp" + url)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            Date start = new Date();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                Date finish = new Date();
                long rsl = finish.getTime() - start.getTime();
                if (rsl < speed) {
                    Thread.sleep(speed - rsl);
                }
                start = new Date();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2 || args[0] == null || args[1] == null) {
            throw new IllegalArgumentException();
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget2(url, speed));
        wget.start();
        wget.join();
    }

}
