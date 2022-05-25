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

public class Receiver {
    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

    private Map<Socket, String> clients;
    private List<Socket> unauthorizedClients;
    private Set<String> userNames;
    private ObjectMapper mapper;
    private RequestHandler requestHandler;
    // AuthorizationListener authorizationListener;
    // MessageListener messageListener;

    public Receiver(RequestHandler requestHandler, ObjectMapper mapper, Map<Socket, String> clients, List<Socket> unauthorizedClients, Set<String> userNames) {
        this.clients = clients;
        this.unauthorizedClients = unauthorizedClients;
        this.userNames = userNames;
        this.mapper = mapper;
        this.requestHandler = requestHandler;
    }

    public void listenClient() {
        while (true) {
            Map<Socket, String> clients;
            synchronized (this.clients) {
                clients = new HashMap<>(this.clients);
            }

            for (Socket client : clients.keySet()) {
                try {
                    logger.debug("Слушаем клиента: {}", client);
                    InputStream inputStream = client.getInputStream();
                    int available = inputStream.available();

                    if (available > 0) {
                        Request request = mapper.readValue(inputStream, Request.class);
                        String userName = clients.get(client);
                        logger.info("Получено сообщение от клиента {}: {}.", userName, request.getData());
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
                }
            }
        }
    }
}
