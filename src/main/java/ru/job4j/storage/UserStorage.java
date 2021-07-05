package ru.job4j.storage;

import net.jcip.annotations.ThreadSafe;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage {

    private final ConcurrentHashMap<Integer, User> users;

    public UserStorage(ConcurrentHashMap<Integer, User> users) {
        this.users = users;
    }

    public boolean add(User user) {
        users.put(user.getId(), new User(user.getId(), user.getAmount()));
        return true;
    }

    public boolean update(User user) {
        users.put(user.getId(), new User(user.getId(), user.getAmount()));
        return true;
    }

    public boolean delete(User user) {
        users.remove(user.getId());
        return true;
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User user1 = new User(fromId, users.get(fromId).getAmount());
        User user2 = new User(toId, users.get(toId).getAmount());
        user1.setAmount(user1.getAmount() - amount);
        user2.setAmount(user2.getAmount() + amount);
        users.put(fromId, user1);
        users.put(toId, user2);
    }
}
