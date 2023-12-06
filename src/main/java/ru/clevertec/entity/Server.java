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

    public Server() {
        this.lock = new ReentrantLock();
        this.resultList = new ArrayList<>();
    }

    public Response processRequest(Request request) {
        delay();

        try {
            lock.lock();
            resultList.add(request.getData());
        } finally {
            lock.unlock();
        }

        System.out.println("Server: Added " + request.getData() + ". List size: " + resultList.size());
        return new Response(resultList.size());
    }

    private void delay() {
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(901) + 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
