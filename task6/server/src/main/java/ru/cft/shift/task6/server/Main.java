package ru.cft.shift.task6.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        logger.info("Приложение запущено.");
        try {
            Server server = new Server(8090);
            Thread serverThread = new Thread(server);
            serverThread.start();

            Scanner sc = new Scanner(System.in);
            sc.nextLine();
            serverThread.interrupt();
            // server.stop();
            logger.info("Завершение работы приложения");
        } catch (Exception e) {
            logger.error("Ошибка", e);
        }
    }
}
