package ru.clevertec.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server {

    @Getter
    private final List<Integer> resultList;
    private final Lock lock;
    private final Random random;

    public Server() {
        this.lock = new ReentrantLock();
        this.resultList = new ArrayList<>();
        this.random = new Random();
    }

    public Response processRequest(Request request) {
        delay();

        try {
            lock.lock();
            resultList.add(request.data());
        } finally {
            lock.unlock();
        }

        System.out.println("Server: Added " + request.data() + ". List size: " + resultList.size());
        return new Response(resultList.size());
    }

    private void delay() {
        try {
            Thread.sleep(random.nextInt(901) + 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
