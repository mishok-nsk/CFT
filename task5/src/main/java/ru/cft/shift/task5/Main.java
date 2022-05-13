package ru.cft.shift.task5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task5.consumers.ConsumerUnion;
import ru.cft.shift.task5.producers.ProducerUnion;
import ru.cft.shift.task5.warehouse.Warehouse;

import java.util.Scanner;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Приложение запущено.");
        MyProperties properties = new MyProperties();
        try {
            logger.info("Читаем .properties.");
            properties.readProperties();
        } catch (Exception e) {
            logger.error("Ошибка при чтении .properties", e);
            logger.info("Приложение завершает работу");
            return;
        }
        logger.info("Создаем склад.");
        Warehouse warehouse = new Warehouse(properties.getStorageSize());
        logger.info("Создаем производителей и потребителей.");
        ProducerUnion producerUnion = new ProducerUnion(properties.getProducerCount(), properties.getProducerTime(), warehouse);
        producerUnion.createProducers();

        ConsumerUnion consumerUnion = new ConsumerUnion(properties.getConsumerCount(), properties.getConsumerTime(), warehouse);
        consumerUnion.createConsumers();

        logger.info("Запускаем производителей и потребителей.");
        producerUnion.startProduce();
        consumerUnion.startConsume();

        Scanner in = new Scanner(System.in);
        in.nextLine(); // Для завершения конвейера необходимо осуществить любой ввод с консоли.
        producerUnion.stop();
        consumerUnion.stop();
    }
}
