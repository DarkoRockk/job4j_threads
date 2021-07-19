package ru.job4j.concurrent;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ParallelIndexSearchTest {

    @Test
    public void whenAllRight() {
        User[] users = new User[100];
        for (int i = 0; i < users.length; i++) {
            users[i] = User.of("name" + i);
        }
        User test = User.of("name5");
        int rsl = ParallelIndexSearch.findIndex(users, test);
        assertThat(rsl, is(5));
    }

    @Test
    public void whenIndexNotExist() {
        User[] users = new User[100];
        for (int i = 0; i < users.length; i++) {
            users[i] = User.of("name" + i);
        }
        User test = User.of("name101");
        int rsl = ParallelIndexSearch.findIndex(users, test);
        assertThat(rsl, is(-1));
    }

}