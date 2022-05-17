package ru.cft.shift.task6.common;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Calendar;
import java.util.TimeZone;

@JsonDeserialize(using = RequestDeserializer.class)
public class Request {
    private final RequestType type;
    // String userName;
    private final Calendar time;
    private final String data;

    public Request(RequestType type, String data) {
        this.type = type;
        time = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public RequestType getRequestType() {
        return type;
    }

    public Calendar getTime() {
        return time;
    }
}
