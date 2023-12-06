package ru.clevertec.entity;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServerTest {
    private final Server server = new Server();

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 20, 100})
    public void testResultListSize(int expected) {
        // when
        for (int i = 1; i <= expected; i++) {
            Request request = new Request(i);
            server.processRequest(request);
        }

        int actual = server.getResultList().size();

        // then
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 20, 100})
    public void testResultListContent(int n) {
        // given
        List<Integer> expected = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            expected.add(i);
        }

        // when
        for (int i = 1; i <= n; i++) {
            Request request = new Request(i);
            server.processRequest(request);
        }

        List<Integer> actual = server.getResultList();

        assertTrue(actual.containsAll(expected));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 20, 100})
    public void testResultListDuplicates(int expected) {
        // when
        for (int i = 1; i <= expected; i++) {
            Request request = new Request(i);
            server.processRequest(request);
        }

        List<Integer> resultList = server.getResultList();

        Set<Integer> actual = new HashSet<>(resultList);

        // then
        assertEquals(expected, actual.size());
    }
}