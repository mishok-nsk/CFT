package ru.cft.shift.task5;

import java.util.ArrayList;
import java.util.List;

public class ProducerUnion {
    private final int producerCount;
    private final int producerTime;
    private final List<Producer> producers;

    public ProducerUnion(int producerCount, int producerTime, Warehouse warehouse) {
        this.producerCount = producerCount;
        this.producerTime = producerTime;
        producers = new ArrayList<>(producerCount);
        Producer.setWarehouse(warehouse);
    }

    public void createProducers() {
        for (int i = 0; i < producerCount; i++) {
            Producer p = new Producer(producerTime);
            p.setName("Producer " + i);
        }
    }

    public void startProduce() {
        for (Producer p : producers) {
            p.start();
        }
    }

}
