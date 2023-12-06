package ru.clevertec.entity;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientTest {
    private final Server server = new Server();

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 20, 100})
    public void testDataSize(int n) {
        // given
        Client client = new Client(n, server);
        int expected = 0;

        // when
        client.sendRequest();

        int actual = client.getData().size();

        // then
        assertEquals(expected, actual);
    }
}