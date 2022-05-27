package ru.cft.shift.task6.server;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    public static final int TIMEOUT = 1000;

    private final int port;
    private final List<Socket> unauthorizedClients = new ArrayList<>();
    private final ConcurrentHashMap<Socket, String> clients = new ConcurrentHashMap<>();
    private final Receiver receiver;

    public Server(int port) {
        this.port = port;
        ObjectMapper mapper = createMapper();
        RequestHandler requestHandler = new RequestHandler(mapper, clients, unauthorizedClients);
        receiver = new Receiver(requestHandler, mapper, clients, unauthorizedClients);
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Thread clientThread = new Thread(receiver::listenClient);
            clientThread.setDaemon(true);
            Thread unauthorizedClientThread = new Thread(receiver::authorizationClient);
            unauthorizedClientThread.setDaemon(true);

            logger.info("Запускаем поток сервера. Host: {}", serverSocket.getInetAddress());
            unauthorizedClientThread.start();
            clientThread.start();
            serverSocket.setSoTimeout(TIMEOUT);

            while (!Thread.interrupted()) {
                Socket socket;
                try {
                    socket = serverSocket.accept();
                } catch (SocketTimeoutException e) {
                    continue;
                }
                logger.info("Клиент подключился.");
                synchronized (unauthorizedClients) {
                    unauthorizedClients.add(socket);
                }
            }
        } catch (IOException e) {
            logger.error("Не удалось создать сокет сервера.", e);
        } finally {
            logger.info("Закрываем сокеты.");
            closeAllClients();
        }
    }

    private ObjectMapper createMapper() {
        JsonFactory jsonFactory = new JsonFactory();
        jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET,false);
        jsonFactory.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
        return new ObjectMapper(jsonFactory);
    }

    private void closeAllClients() {
        for (Socket client : clients.keySet()) {
            try {
                client.close();
            } catch (Exception ignore) {
            }
        }
        clients.clear();

        synchronized (unauthorizedClients) {
            for (Socket client : unauthorizedClients) {
                try {
                    client.close();
                } catch (Exception ignore) {
                }
            }
            unauthorizedClients.clear();
        }
    }

}
