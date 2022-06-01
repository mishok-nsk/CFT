package ru.cft.shift.task5.consumers;

import ru.cft.shift.task5.warehouse.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class ConsumerUnion {
    private final int consumerCount;
    private final int consumerTimeSecond;
    private final List<Thread> consumers;

    public ConsumerUnion(int consumerCount, int consumerTimeSecond, Warehouse warehouse) {
        this.consumerCount = consumerCount;
        this.consumerTimeSecond = consumerTimeSecond;
        consumers = new ArrayList<>(consumerCount);
        Consumer.setWarehouse(warehouse);
    }

    public void createConsumers() {
        for (int i = 0; i < consumerCount; i++) {
            Consumer c = new Consumer(consumerTimeSecond);
            Thread consumerThread = new Thread(c);
            consumerThread.setName("Consumer " + i);
            consumers.add(consumerThread);
        }
    }

    public void startConsume() {
        for (Thread c : consumers) {
            c.start();
        }
    }

    public void stop() {
        for (Thread c : consumers) {
            c.interrupt();
        }
    }
}
