package ru.cft.shift.task6.client.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public class ServerListener implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ServerListener.class);
    private final InputStream inputStream;
    private final ResponseHandler responseHandler;

    public ServerListener(InputStream inputStream, ResponseHandler responseHandler) {
        this.inputStream = inputStream;
        this.responseHandler = responseHandler;
    }

    public void run() {
        while (!Thread.interrupted()) {
            try {
                int available = inputStream.available();
                if (available > 0) {
                    logger.info("Получен ответ от сервера.");
                    String jsonResponse = new String(inputStream.readNBytes(available), StandardCharsets.UTF_8);
                    responseHandler.handleResponse(jsonResponse);
                }
            } catch (IOException e) {
                logger.error("Слушателю не удалось подключиться к серверу.", e);
            }
        }
    }
}
