package ru.cft.shift.task5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {

        MyProperties properties = new MyProperties();
        properties.readProperties();
        Warehouse warehouse = new Warehouse(properties.getStorageSize());
        ProducerUnion producerUnion = new ProducerUnion(properties.getProducerCount(), properties.getProducerTime(), warehouse);
        producerUnion.createProducers();

        ConsumerUnion consumerUnion = new ConsumerUnion(properties.getConsumerCount(), properties.getConsumerTime(), warehouse);
        consumerUnion.createConsumers();

        producerUnion.startProduce();
        consumerUnion.startConsume();
    }
}
