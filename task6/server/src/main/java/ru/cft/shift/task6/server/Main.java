package ru.cft.shift.task6.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task6.common.ChatProperties;

import java.util.Scanner;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Приложение запущено.");
        try {
            Server server = new Server(ChatProperties.readPort());
            Thread serverThread = new Thread(server);
            serverThread.start();

            Scanner sc = new Scanner(System.in);
            sc.nextLine();
            server.stop();
            logger.info("Завершение работы приложения");
        } catch (IllegalStateException e) {
            logger.error("Ошибка запуска потока сервера", e);
        }
    }
}
