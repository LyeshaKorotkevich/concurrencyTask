package ru.clevertec;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.clevertec.entity.Client;
import ru.clevertec.entity.Server;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientServerIntegrationTest {
    private final Server server = new Server();

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 20, 100})
    void requestShouldCalculateAccumulator(int n) {
        // given
        Client client = new Client(n, server);
        Integer expected = ((1 + n) * (n) / 2);

        // when
        client.sendRequest();
        Integer actual = client.getAccumulator().get();

        // then
        assertEquals(expected, actual);
    }
}
