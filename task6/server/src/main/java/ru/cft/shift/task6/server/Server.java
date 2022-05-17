package ru.cft.shift.task6.server;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task6.common.Request;
import ru.cft.shift.task6.common.RequestType;
import ru.cft.shift.task6.common.Response;
import ru.cft.shift.task6.common.ResponseType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    public static final String USER_NAME_ERROR = "Пользователь с таким именем уже в чате!";
    private final int port;
    private final List<Socket> unauthorizedClients = new ArrayList<>();
    private final Map<Socket, String> clients = new HashMap<>();
    private final Set<String> userNames = new HashSet<>();
    private final ObjectMapper mapper;

    public Server(int port) {
        this.port = port;
        JsonFactory jsonFactory = new JsonFactory();
        jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET,false);
        jsonFactory.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
        mapper = new ObjectMapper(jsonFactory);
    }

    public void startServer() {
        Thread clientThread = new Thread(this::processClient);
        clientThread.setDaemon(true);

        Thread unauthorizedClientThread = new Thread(this::authorizationClient);
        unauthorizedClientThread.setDaemon(true);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Запускаем поток сервера. Host: {}", serverSocket.getInetAddress());
            unauthorizedClientThread.start();
            clientThread.start();

            while (true) {
                logger.info("Сервер ожидает подключения клиентов.");
                Socket socket = serverSocket.accept();
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

    public void processClient() {
        while (true) {
            Map<Socket, String> clients;
            synchronized (this.clients) {
                clients = new HashMap<>(this.clients);
            }

            for (Socket client : clients.keySet()) {
                try {
                    logger.debug("Слушаем клиента: {}", client);
                    // logger.debug("Socket is close: {}", client.isClosed());
                    InputStream inputStream = client.getInputStream();
                    int available = inputStream.available();

                    if (available > 0) {
                        // byte[] bytes = inputStream.readNBytes(available);
                        Request request = mapper.readValue(inputStream, Request.class);
                        String userName = clients.get(client);
                        logger.info("Получено сообщение от клиента {}: {}.", userName, request.getData());
                        Response response = new Response(ResponseType.MESSAGE, userName, request.getData());
                        clients.keySet().forEach(outputClient -> {
                            try {
                                mapper.writeValue(outputClient.getOutputStream(), response);
                            } catch (IOException e) {
                                logger.error("Нет связи с клиентом.", e);
                                synchronized (this.clients) {
                                    this.clients.remove(client);
                                }
                            }
                        });
                        // OutputStream outputStream = client.getOutputStream();
                        // outputStream.write(bytes);
                        // outputStream.flush();
                    }
                } catch (IOException e) {
                    logger.error("Нет связи с клиентом.", e);
                    synchronized (this.clients) {
                        this.clients.remove(client);
                    }
                }
            }
        }
    }

    private void authorizationClient() {
        while (true) {
            List<Socket> unknownClients;
            synchronized (unauthorizedClients) {
                unknownClients = new ArrayList<>(unauthorizedClients);
            }

            for (Socket client : unknownClients) {
                try {
                    logger.debug("Слушаем клиента: {}", client);
                    InputStream inputStream = client.getInputStream();
                    int available = inputStream.available();

                    if (available > 0) {
                        Request request = mapper.readValue(inputStream, Request.class);
                        if (request.getRequestType() != RequestType.AUTHORIZATION) {
                            continue;
                        }
                        String userName = request.getData();
                        logger.info("Авторизация клиента {}.", userName);
                        Response response;
                        if (userNames.contains(userName)) {
                            response = new Response(ResponseType.AUTHORIZATION_ERROR, userName, USER_NAME_ERROR);
                            mapper.writeValue(client.getOutputStream(), response);
                            logger.info("Логин {} уже занят.", userName);
                        } else {
                            response = new Response(ResponseType.AUTHORIZATION_OK, userName, "");
                            mapper.writeValue(client.getOutputStream(), response);
                            logger.info("Клиент {} авторизирован.", userName);
                            userNames.add(userName);
                            synchronized (unauthorizedClients) {
                                unauthorizedClients.remove(client);
                            }
                            synchronized (clients) {
                                clients.put(client, userName);
                            }
                        }
                    }
                } catch (IOException e) {
                    logger.error("Ошибка ввода/вывода.", e);
                }
            }
        }
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
