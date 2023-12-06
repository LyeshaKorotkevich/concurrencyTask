package ru.clevertec.entity;

import lombok.Getter;
import ru.clevertec.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Client {

    @Getter
    private final List<Integer> data;
    private final Server server;

    @Getter
    private final AtomicInteger accumulator;
    private final ExecutorService executor;

    public Client(int size, Server server) {
        this.server = server;
        this.accumulator = new AtomicInteger();
        this.executor = Executors.newFixedThreadPool(Constants.THREAD_COUNT);
        data = new ArrayList<>(size);

        for (int i = 1; i <= size; i++) {
            data.add(i);
        }
    }

    public void sendRequest() {
        Random random = new Random();
        while (!data.isEmpty()) {
            int randomIndex = random.nextInt(data.size());
            int value = data.remove(randomIndex);
            Request request = new Request(value);
            System.out.println("Client: Removed " + value + ". List size: " + data.size());

            CompletableFuture<Response> responseFuture = CompletableFuture.supplyAsync(
                    () -> server.processRequest(request),
                    executor
            );

            responseFuture.thenAccept(response -> accumulator.addAndGet(response.getSize()));

            delay();
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ignored) {
        }
    }

    private void delay() {
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(401) + 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
