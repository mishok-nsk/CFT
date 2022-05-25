package ru.cft.shift.task6.common;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Calendar;
import java.util.TimeZone;

@JsonDeserialize(using = ResponseDeserializer.class)
public class Response {
    private final ResponseType type;
    private final String userName;
    private final Calendar time;
    private final String data;

    public Response(ResponseType type, String userName, String data) {
        this.type = type;
        this.userName = userName;
        time = Calendar.getInstance(TimeZone.getTimeZone("GMT+7:00"));
        this.data = data;
    }

    public void setTimeInMillis(long millis) {
        time.setTimeInMillis(millis);
    }

    public String getUserName() {
        return userName;
    }

    public Calendar getTime() {
        return time;
    }

    public String getData() {
        return data;
    }

    public ResponseType getType() {
        return type;
    }
}
