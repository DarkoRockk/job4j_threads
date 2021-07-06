package ru.job4j.storage;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage {

    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

    public boolean add(User user) {
        return users.putIfAbsent(user.getId(), user) == null;
    }

    public boolean update(User user) {
        return users.replace(user.getId(), user) != null;
    }

    public boolean delete(User user) {
        return users.remove(user.getId(), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        User user1 = users.get(fromId);
        User user2 = users.get(toId);
        if (user1 != null && user2 != null) {
            if (user1.getAmount() >= amount) {
                user1.setAmount(user1.getAmount() - amount);
                user2.setAmount(user2.getAmount() + amount);
                rsl = true;
            }
        }
        return rsl;
    }
}
