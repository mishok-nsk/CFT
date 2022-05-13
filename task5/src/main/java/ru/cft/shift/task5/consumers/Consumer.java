package ru.cft.shift.task5.consumers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task5.warehouse.Warehouse;

public class Consumer extends Thread {
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
                int product = warehouse.getFromStock();
                sleep(time * 1000L);
                logger.info("Поток {} потребил ресурс {}.", Thread.currentThread().getName(), product);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
