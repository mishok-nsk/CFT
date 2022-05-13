package ru.cft.shift.task5.producers;

import ru.cft.shift.task5.warehouse.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class ProducerUnion {
    private final int producerCount;
    private final int producerTimeSecond;
    private final List<Producer> producers;

    public ProducerUnion(int producerCount, int producerTimeSecond, Warehouse warehouse) {
        this.producerCount = producerCount;
        this.producerTimeSecond = producerTimeSecond;
        producers = new ArrayList<>(producerCount);
        Producer.setWarehouse(warehouse);
    }

    public void createProducers() {
        for (int i = 0; i < producerCount; i++) {
            Producer p = new Producer(producerTimeSecond);
            p.setName("Producer " + i);
            producers.add(p);
        }
    }

    public void startProduce() {
        for (Producer p : producers) {
            p.start();
        }
    }

    public void stop() {
        for (Producer p : producers) {
            p.interrupt();
        }
    }

}
