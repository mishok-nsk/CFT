package ru.cft.shift.task5;

import java.util.ArrayList;
import java.util.List;

public class ConsumerUnion {
    private final int consumerCount;
    private final int consumerTime;
    private final List<Consumer> consumers;

    public ConsumerUnion(int consumerCount, int consumerTime, Warehouse warehouse) {
        this.consumerCount = consumerCount;
        this.consumerTime = consumerTime;
        consumers = new ArrayList<>(consumerCount);
        Consumer.setWarehouse(warehouse);
    }

    public void createConsumers() {
        for (int i = 0; i < consumerCount; i++) {
            Consumer c = new Consumer(consumerTime);
            c.setName("Consumer " + i);
        }
    }

    public void startConsume() {
        for (Consumer c : consumers) {
            c.start();
        }
    }
}
