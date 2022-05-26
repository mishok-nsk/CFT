package ru.cft.shift.task6.server;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task6.common.Response;
import ru.cft.shift.task6.common.ResponseType;

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
    private final Set<String> userNames = new HashSet<>();
    private final ObjectMapper mapper;
    private final Receiver receiver;
    private final RequestHandler requestHandler;
    private boolean isStop;

    public Server(int port) {
        this.port = port;
        JsonFactory jsonFactory = new JsonFactory();
        jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET,false);
        jsonFactory.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
        mapper = new ObjectMapper(jsonFactory);
        requestHandler = new RequestHandler(mapper, clients, unauthorizedClients, userNames);
        receiver = new Receiver(requestHandler, mapper, clients, unauthorizedClients, userNames);
    }

    public void run() {
        Thread clientThread = new Thread(receiver::listenClient);
        clientThread.setDaemon(true);

        Thread unauthorizedClientThread = new Thread(receiver::authorizationClient);
        unauthorizedClientThread.setDaemon(true);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Запускаем поток сервера. Host: {}", serverSocket.getInetAddress());
            unauthorizedClientThread.start();
            clientThread.start();
            serverSocket.setSoTimeout(TIMEOUT);

            while (!isStop) {
                // logger.info("Сервер ожидает подключения клиентов.");
                Socket socket;
                try {
                    socket = serverSocket.accept();
                } catch (SocketTimeoutException e) {
                    continue;
                }
                logger.info("Клиент подключился. {}", socket);
                synchronized (unauthorizedClients) {
                    unauthorizedClients.add(socket);
                }
            }
        } catch (IOException e) {

        } finally {
            closeAllClients();
        }
    }

    public void stop() {
        isStop = true;
    }

    private void closeAllClients() {
        synchronized (clients) {
            for (Socket client : clients.keySet()) {
                try {
                    client.close();
                } catch (Exception ignore) {
                }
            }
            clients.clear();
        }
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
