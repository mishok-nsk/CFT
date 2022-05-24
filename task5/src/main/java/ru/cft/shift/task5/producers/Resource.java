package ru.cft.shift.task5.producers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Resource {
    private static volatile int count = 1;
    private final int id;

    public static synchronized Resource produce() {
        return new Resource(count++);
    }

    private Resource(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "product_id[" + id + "]";
    }
}
