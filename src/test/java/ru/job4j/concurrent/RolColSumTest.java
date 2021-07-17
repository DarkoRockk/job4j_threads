package ru.job4j.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class RolColSumTest {

    @Test
    public void whenSum() {
        int[][] matrix = {{1, 2}, {3, 4}};
        RolColSum.Sums[] rsl = RolColSum.sum(matrix);
        assertThat(rsl.length, is(2));
        assertThat(rsl[0], is(new RolColSum.Sums(3, 4)));
        assertThat(rsl[1], is(new RolColSum.Sums(7, 6)));
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2}, {3, 4}};
        RolColSum.Sums[] rsl = RolColSum.asyncSum(matrix);
        assertThat(rsl.length, is(2));
        assertThat(rsl[0], is(new RolColSum.Sums(3, 4)));
        assertThat(rsl[1], is(new RolColSum.Sums(7, 6)));
    }
}