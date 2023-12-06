package ru.clevertec;

import ru.clevertec.entity.Client;
import ru.clevertec.entity.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        Client client = new Client(100, server);
        client.sendRequest();
    }
}