package ru.cft.shift.task6.client.model;

import java.util.Calendar;

public interface MessageListener {
    void addNewMessage(String userName, Calendar time, String message);
}
