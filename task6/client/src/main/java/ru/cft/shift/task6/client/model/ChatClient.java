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
import java.io.PrintWriter;
import java.net.Socket;
import ru.cft.shift.task6.common.*;

public class ChatClient {
    private static final Logger logger = LoggerFactory.getLogger(ChatClient.class);
    public static final String CHARSET_NAME = "UTF-8";
    private final int port;
    private String host = "127.0.0.1";
    private String userName = "anonymous";
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    ObjectMapper mapper;
    // private String message = " ";
    private Thread serverListenerThread;
    private MessageListener messageListener;
    private ConnectListener connectListener;
    private AuthorizationListener authorizationListener;

    public ChatClient(int port) {
        this.port = port;
        JsonFactory jsonFactory = new JsonFactory();
        jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET,false);
        jsonFactory.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
        mapper = new ObjectMapper(jsonFactory);
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setUserName(String userName) {
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
            // PrintWriter writer= new PrintWriter(outputStream);
            mapper.writeValue(outputStream, authRequest);
            String request = mapper.writeValueAsString(authRequest);
            logger.info("запрос: {}", request);
            // outputStream.write(userName.getBytes(CHARSET_NAME));
            // outputStream.flush();
            Thread.currentThread().sleep(1000);
            int available = inputStream.available();
            if (available > 0) {
                Response response = mapper.readValue(inputStream, Response.class);
                if (response.getType() == ResponseType.AUTHORIZATION_OK) {
                    logger.info("Авторизация прошла успешно.");
                    serverListenerThread.start();
                } else {
                    logger.info("Авторизация не удалась: {}", response.getData());
                }
                authorizationListener.getResponse(response.getData());
            } else {
                authorizationListener.getResponse("Server not response!");
            }
        } catch (IOException e) {
            logger.error("Не удалось отправить сообщение на сервер", e);
        } catch (InterruptedException e) {

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
            // serverListenerThread.start();
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
            messageListener.addNewMessage("Вы", request.getTime(), message);
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
                socket.close();
            }
        } catch (SecurityException e) {
            logger.error("Ошибка остановки потока: ", e);
        } catch (IOException e) {
            logger.error("Ошибка закрытия сокета: ", e);
        }
    }

    private void listenServer() {
        logger.info("Socket is close: {}", socket.isClosed());
        while (true) {
            try {
                int available = inputStream.available();
                if (available > 0) {
                    logger.info("Получено сообщение от сервера.");
                    Response response = mapper.readValue(inputStream, Response.class);
                    messageListener.addNewMessage(response.getUserName(), response.getTime(), response.getData());
                }
            } catch (IOException e) {
                logger.error("Слушателю не удалось подключиться к серверу.", e);
            }
        }
    }
}
