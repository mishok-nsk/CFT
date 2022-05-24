package ru.cft.shift.task5.warehouse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task5.producers.Resource;

import java.util.ArrayDeque;
import java.util.Queue;

public class Warehouse {
    private static final Logger logger = LoggerFactory.getLogger(Warehouse.class);
    private final int storageSize;
    private final Queue<Resource> products;

    public Warehouse(int storageSize) {
        this.storageSize = storageSize;
        products = new ArrayDeque<>(storageSize);
        logger.info("Создан склад емкостью {} единиц", storageSize);
    }

    public boolean isFull() {
        return products.size() == storageSize;
    }

    public synchronized void putInStock(Resource product) throws InterruptedException {
        while (isFull()) {
            logger.info("Склад полон. Поток {} ждёт освобождения места.", Thread.currentThread().getName());
            wait();
            logger.info("На складе освободили место. Поток {} помещает ресурс на склад.", Thread.currentThread().getName());
        }

        products.add(product);
        logger.info("Поток {} поставил ресурс {} на сток.", Thread.currentThread().getName(), product);
        logger.info("Остаток ресурсов на складе {} единиц.", products.size());
        notifyAll();
    }

    public boolean isEmpty() {
        return products.size() == 0;
    }

    public synchronized Resource getFromStock() throws InterruptedException {
        while (isEmpty()) {
            logger.info("Склад пуст. Поток {} ждет ресурсы.", Thread.currentThread().getName());
            wait();
            logger.info("Ресурсы произведены. Поток {} начал потребление.", Thread.currentThread().getName());
        }
        Resource product = products.remove();
        logger.info("Поток {} взял ресурс {} со стока.", Thread.currentThread().getName(), product);
        logger.info("Остаток ресурсов на складе {} единиц.", products.size());
        notifyAll();
        return product;
    }
}
