package ru.cft.shift.task6.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task6.common.*;

import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.Thread.sleep;

public class RequestHandler {
    public static final String USER_NAME_ERROR = "Пользователь с таким именем уже в чате!";
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final ObjectMapper mapper;
    private final ConcurrentHashMap<Socket, String> clients;
    private final List<Socket> unauthorizedClients;
    private final Set<String> userNames = new HashSet<>();

    public RequestHandler(ObjectMapper mapper, ConcurrentHashMap<Socket, String> clients, List<Socket> unauthorizedClients) {
        this.mapper = mapper;
        this.clients = clients;
        this.unauthorizedClients = unauthorizedClients;
    }

    public void sendAllClients(Socket client, Request request) {
        String userName = clients.get(client);
        if (request.getRequestType() == RequestType.EXIT) {
            deleteDisconnectedClient(client);
            logger.info("Пользователь {} закрыл приложение.", userName);
            return;
        }
        logger.info("Получено сообщение от клиента {}: {}.", userName, request.getData());
        Message message = new Message(userName, request.getData());
        message.setTime(request.getTime());
        Response<Message> response = new Response<>(ResponseType.MESSAGE);
        response.setData(message);
        clients.keySet().forEach(outputClient -> {
            try {
                if (client != outputClient) {
                    mapper.writeValue(outputClient.getOutputStream(), response);
                }
            } catch (IOException e) {
                logger.error("Нет связи с клиентом.", e);
                deleteDisconnectedClient(outputClient);
            }
        });
    }

    public void authorizationClient(Socket client, Request request) throws InterruptedException {
        try {
            String userName = request.getData();
            logger.info("Авторизация клиента {}.", userName);
            Response<String> response;
            if (userNames.contains(userName)) {
                response = new Response(ResponseType.AUTHORIZATION_ERROR);
                response.setData(USER_NAME_ERROR);
                mapper.writeValue(client.getOutputStream(), response);
                logger.info("Логин {} уже занят.", userName);
            } else {
                response = new Response(ResponseType.AUTHORIZATION_OK);
                logger.info("Клиент {} авторизирован.", userName);
                mapper.writeValue(client.getOutputStream(), response);
                sleep(100);
                sendUserNames(client);
                addNewClient(client, userName);
            }
        } catch (IOException e) {
            logger.error("Нет связи с клиентом.", e);
            deleteDisconnectedClient(client);
        }
    }

    public void deleteDisconnectedClient(Socket client) {
        String name;
        name = this.clients.remove(client);
        userNames.remove(name);
        sendToAllUserStatus(name, ResponseType.USER_OUT);
        logger.info("Пользователь {} отключился от сервера.", name);
    }

    private void sendToAllUserStatus(String userName, ResponseType type) {
        Response<String> response = new Response(type);
        response.setData(userName);
        clients.keySet().forEach(outputClient -> {
            try {
                mapper.writeValue(outputClient.getOutputStream(), response);
            } catch (IOException e) {
                logger.error("Нет связи с клиентом.", e);
                deleteDisconnectedClient(outputClient);
            }
        });
    }

    private void addNewClient(Socket client, String userName) {
        userNames.add(userName);
        synchronized (unauthorizedClients) {
            unauthorizedClients.remove(client);
        }
        sendToAllUserStatus(userName, ResponseType.USER_IN);
        clients.put(client, userName);
    }

    private void sendUserNames(Socket receiver) {
        try {
            Response<Set<String>> response = new Response(ResponseType.USER_LIST);
            response.setData(userNames);
            mapper.writeValue(receiver.getOutputStream(), response);
        } catch (IOException e) {
            logger.error("Нет связи с клиентом.", e);
            deleteDisconnectedClient(receiver);
        }
    }
}
