package ru.cft.shift.task5.consumers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task5.producers.Resource;
import ru.cft.shift.task5.warehouse.Warehouse;

import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);
    private static Warehouse warehouse;
    private final int time;

    public static void setWarehouse(Warehouse inWarehouse) {
        warehouse = inWarehouse;
    }

    public Consumer(int time) {
        this.time = time;
    }

    public void run() {
        logger.info("Поток {} запущен.", Thread.currentThread().getName());
        while (!Thread.interrupted()) {
            try {
                Resource product = warehouse.getFromStock();
                TimeUnit.SECONDS.sleep(time);
                logger.info("Поток {} потребил ресурс {}.", Thread.currentThread().getName(), product);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
