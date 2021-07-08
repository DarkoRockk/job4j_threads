package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAdd() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 0);
        Base model2 = new Base(2, 0);
        Base model3 = new Base(3, 0);
        cache.add(model1);
        cache.add(model2);
        cache.add(model3);
        assertThat(cache.get(model1.getId()), is(model1));
        assertThat(cache.get(model2.getId()), is(model2));
        assertThat(cache.get(model3.getId()), is(model3));
    }

    @Test
    public void whenUpdate() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 0);
        Base model2 = new Base(1, 0);
        model2.setName("test");
        cache.add(model1);
        assertThat(cache.get(model1.getId()), is(model1));
        cache.update(model2);
        assertThat(cache.get(model1.getId()).getName(), is(model2.getName()));
    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 0);
        cache.add(model1);
        assertThat(cache.get(model1.getId()), is(model1));
        cache.delete(model1);
        assertNull(cache.get(model1.getId()));
    }
}