package ru.cft.shift.task6.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task6.common.Request;
import ru.cft.shift.task6.common.RequestType;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("InfiniteLoopStatement")
public class Receiver {
    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

    private final ConcurrentHashMap<Socket, String> clients;
    private final List<Socket> unauthorizedClients;
    private final ObjectMapper mapper;
    private final RequestHandler requestHandler;

    public Receiver(RequestHandler requestHandler, ObjectMapper mapper, ConcurrentHashMap<Socket, String> clients, List<Socket> unauthorizedClients) {
        this.clients = clients;
        this.unauthorizedClients = unauthorizedClients;
        this.mapper = mapper;
        this.requestHandler = requestHandler;
    }

    public void listenClient() {
        while (true) {
            Map<Socket, String> clients;
            clients = new HashMap<>(this.clients);

            for (Socket client : clients.keySet()) {
                try {
                    logger.debug("Слушаем клиента: {}", client);
                    InputStream inputStream = client.getInputStream();
                    int available = inputStream.available();

                    if (available > 0) {
                        Request request = mapper.readValue(inputStream, Request.class);
                        requestHandler.sendAllClients(client, request);
                    }
                } catch (IOException e) {
                    logger.error("Нет связи с клиентом.", e);
                    requestHandler.deleteDisconnectedClient(client);
                }
            }
        }
    }

    public void authorizationClient() {
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
                        requestHandler.authorizationClient(client, request);
                    }
                } catch (IOException e) {
                    logger.error("Нет связи с клиентом.", e);
                    synchronized (unauthorizedClients){
                        unauthorizedClients.remove(client);
                    }
                } catch (InterruptedException e) {
                    logger.error("Работа потока прервана.", e);
                    return;
                }
            }
        }
    }
}
