package ru.cft.shift.task6.client.model;

import ru.cft.shift.task6.common.Message;


public interface MessageListener {
    void addNewMessage(Message message);
    void addUserMessage(String userName, String message);
    void updateClientList(String userList);
}
