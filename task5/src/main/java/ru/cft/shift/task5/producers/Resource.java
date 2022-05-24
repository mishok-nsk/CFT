package ru.cft.shift.task5.producers;

public class Resource {
    private static volatile int id;

    public synchronized int getAndIncrement() {
        return id++;
    }

    public int getId() {
        return id;
    }
}
