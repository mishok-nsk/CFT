package ru.cft.shift.task6.common;

import java.util.Calendar;
import java.util.TimeZone;

public class Message {
    private String userName;
    private Calendar time;
    private String text;

    public Message(String userName, String text) {
        this.userName = userName;
        this.text = text;
        time = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    }

    public Message() {
        time = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public Calendar getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "User: " + userName + " Текст: " + text;
    }
}
