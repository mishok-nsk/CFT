package ru.cft.shift.task6.server;

import ru.cft.shift.task6.common.Request;

public interface MessageListener {
    void sendMessage(Request request);
}
