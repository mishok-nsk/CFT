package ru.cft.shift.task6.client.controller;

import ru.cft.shift.task6.client.model.ChatClient;

public class ChatClientController {
    private final ChatClient chatClient;

    public ChatClientController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public void sendMessage(String message) {
        chatClient.sendMessage(message);
    }

    public void chatExit() {
        chatClient.stop();
    }

    public void userNameEntered(String name) {
        chatClient.setUserName(name);
    }

    public void hostEntered(String host) {
        chatClient.setHost(host);
        chatClient.connect();
    }
}
