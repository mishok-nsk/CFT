package ru.cft.shift.task6.client.model;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import ru.cft.shift.task6.common.*;

public class ChatClient {
    private static final Logger logger = LoggerFactory.getLogger(ChatClient.class);

    public static final String YOU = "Вы";
    public static final String USER_OUT_MESSAGE = "покинул(а) чат!";
    public static final String USER_IN_MESSAGE = "присоединил(ся/ась) к чату!";
    public static final String USER_NAME_BLANK = "имя пользователя пустое";

    private final int port;
    private String host = "127.0.0.1";
    private String userName = "anonymous";
    private Set<String> clientList = new HashSet<>();
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    ObjectMapper mapper;

    private Thread serverListenerThread;
    private MessageListener messageListener;
    private ConnectListener connectListener;
    private AuthorizationListener authorizationListener;
    // private ClientListener clientListener;

    public ChatClient(int port) {
        this.port = port;
        initMapper();
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setUserName(String userName) {
        if (userName.isBlank()) {
            authorizationListener.getResponse(UIString.encoding(USER_NAME_BLANK));
            return;
        }
        this.userName = userName;
        authorization();
    }

    public void setMessageListener(MessageListener listener) {
        messageListener = listener;
    }

    public void setConnectListener(ConnectListener listener) {
        connectListener = listener;
    }

    public void setAuthorizationListener(AuthorizationListener authorizationListener) {
        this.authorizationListener = authorizationListener;
    }

    private void authorization() {
        try {
            Request authRequest = new Request(RequestType.AUTHORIZATION, userName);
            mapper.writeValue(outputStream, authRequest);

        } catch (IOException e) {
            logger.error("Не удалось отправить сообщение на сервер", e);
        }
    }

    public void connect() {
        serverListenerThread = new Thread(this::listenServer);
        serverListenerThread.setDaemon(true);

        try {
            socket = new Socket(host, port);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            connectListener.connectionAction(true);
            serverListenerThread.start();
        } catch (IOException e) {
            logger.error("Не удалось подключиться к серверу.", e);
            connectListener.connectionAction(false);
        }
    }

    public void sendMessage(String message) {
        try {
            logger.info("Отправляем сообщение на сервер");
            Request request = new Request(RequestType.MESSAGE, message);
            mapper.writeValue(outputStream, request);
            Message messageData = new Message(UIString.encoding(YOU), message);
            messageListener.addNewMessage(messageData);
        } catch (IOException e) {
            logger.error("Не удалось отправить сообщение на сервер", e);
        }
    }

    public void stop() {
        try {
            if (serverListenerThread != null) {
                serverListenerThread.interrupt();
            }
            if (socket != null) {
                mapper.writeValue(outputStream, new Request(RequestType.EXIT, ""));
                socket.close();
            }
        } catch (SecurityException e) {
            logger.error("Ошибка остановки потока: ", e);
        } catch (IOException e) {
            logger.error("Ошибка закрытия сокета: ", e);
        }
    }

    private void initMapper() {
        JsonFactory jsonFactory = new JsonFactory();
        jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET,false);
        jsonFactory.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
        mapper = new ObjectMapper(jsonFactory);
    }

    private String clientListToString() {
        StringBuilder builder = new StringBuilder(clientList.size() * 5);
        clientList.forEach(client -> builder.append(client).append("\n"));
        return builder.toString();
    }

    private void setUserList(Set<String> userList) {
        clientList = new HashSet<>(userList);
        messageListener.updateClientList(clientListToString());
    }

    private void putClientInList(String clientName) {
        logger.info("Добавление юзера {} в список пользователей.", clientName);
        clientList.add(clientName);
        messageListener.updateClientList(clientListToString());
    }

    private void removeClientFromList(String clientName) {
        clientList.remove(clientName);
        messageListener.updateClientList(clientListToString());
    }

    private void listenServer() {
        logger.info("Socket is close: {}", socket.isClosed());
        while (true) {
            try {
                int available = inputStream.available();
                if (available > 0) {
                    logger.info("Получено сообщение от сервера.");
                    String jsonResponse = new String(inputStream.readNBytes(available), StandardCharsets.UTF_8);
                    handleResponse(jsonResponse);
                }
            } catch (IOException e) {
                logger.error("Слушателю не удалось подключиться к серверу.", e);
            }
        }
    }

    private void handleResponse(String jsonResponse) {
        Response<?> response = parseResponse(jsonResponse);
        switch (response.getType()) {
            case USER_LIST -> setUserList((HashSet<String>) response.getData());
            case USER_IN -> {
                String userName = (String) response.getData();
                messageListener.addUserMessage(userName, UIString.encoding(USER_IN_MESSAGE));
                putClientInList(userName);
            }
            case USER_OUT -> {
                String userName = (String) response.getData();
                messageListener.addUserMessage(userName, UIString.encoding(USER_OUT_MESSAGE));
                removeClientFromList(userName);
            }
            case MESSAGE -> messageListener.addNewMessage((Message) response.getData());
            case AUTHORIZATION_OK -> logger.info("Авторизация прошла успешно.");
            case AUTHORIZATION_ERROR -> {
                logger.info("Авторизация не удалась: {}", response.getData());
                authorizationListener.getResponse((String) response.getData());
            }
            default -> logger.error("Неизвестный тип ответа от сервера.");
        }
    }

    private Response<?> parseResponse(String jsonResponse) {
        try {
            String types = mapper.readTree(jsonResponse).get("type").asText();
            ResponseType type = ResponseType.MESSAGE;
            for (ResponseType stdType : ResponseType.values()) {
                if (stdType.name().equals(types)) {
                    type = stdType;
                }
            }
            logger.info("Json {}", jsonResponse);
            Response<?> response;
            switch (type) {
                case MESSAGE -> response = Response.read(jsonResponse, mapper, Message.class);
                case USER_IN, USER_OUT, AUTHORIZATION_ERROR, AUTHORIZATION_OK -> response = Response.read(jsonResponse, mapper, String.class);
                case USER_LIST -> response = Response.read(jsonResponse, mapper, HashSet.class);
                default -> throw new Exception("Неизвестный тип ответа от сервера.");
            }
            logger.info("Сообщение: {}, {}", response.getType(), response.getData());
            return response;
        } catch (Exception e) {
            return new Response<>(null);
        }
    }
}
