package ru.cft.shift.task5.producers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task5.warehouse.Warehouse;

import java.util.concurrent.TimeUnit;

public class Producer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private static Warehouse warehouse;

    private final int time;

    public static void setWarehouse(Warehouse inWarehouse) {
        warehouse = inWarehouse;
    }
    public Producer(int time) {
        this.time = time;
    }

    public void run() {
        logger.info("Поток {} запущен.", Thread.currentThread().getName());
        while (!Thread.interrupted()) {
            try {
                TimeUnit.SECONDS.sleep(time);
                Resource product = Resource.produce();
                logger.info("Поток {} произвел ресурс {}.", Thread.currentThread().getName(), product);
                warehouse.putInStock(product);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
