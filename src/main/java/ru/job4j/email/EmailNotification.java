package ru.job4j.email;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    private final String subjectTemp = "Notification {%s} to email {%s}.";
    private final String bodyTemp = "Add new event to {%s}.";

    boolean emailTo(User user) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                String username = user.getUsername();
                String email = user.getEmail();
                String subject = String.format(subjectTemp, username, email);
                String body = String.format(bodyTemp, username);
                send(subject, body, email);
            }
        });
        return true;
    }

    boolean close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return true;
    }

    void send(String subject, String body, String email) {
    }
}
