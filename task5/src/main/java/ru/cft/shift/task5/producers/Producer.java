package ru.cft.shift.task5.producers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task5.warehouse.Warehouse;

public class Producer extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private static final Object SYNC_PROD = new Object();
    private static Warehouse warehouse;
    private static int idProduct;
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
                sleep(time * 1000L);
                logger.info("Поток {} произвел ресурс {}.", Thread.currentThread().getName(), idProduct);
                synchronized (SYNC_PROD) {
                    warehouse.putInStock(idProduct++);
                }
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
