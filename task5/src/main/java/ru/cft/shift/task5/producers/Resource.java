package ru.cft.shift.task5.producers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Resource {
    private static final Logger logger = LoggerFactory.getLogger(Resource.class);
    private static volatile int id = 1;

    public synchronized int produce() {
        logger.info("Поток {} произвел ресурс {}.", Thread.currentThread().getName(), id);
        return id++;
    }
}
