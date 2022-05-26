package ru.cft.shift.task6.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.TimeZone;

// @JsonDeserialize(using = ResponseDeserializer.class)
public class Response<Data> {
    private ResponseType type;
    private Data data;
    //private final String userName;
    //private final Calendar time;
    //private final String data;

    public Response() {

    }

    public Response(ResponseType type) {
        this.type = type;
        //this.userName = userName;
        //time = Calendar.getInstance(TimeZone.getTimeZone("GMT+7:00"));
        //this.data = data;
    }

    public Data getData() {
        return data;
    }

    public ResponseType getType() {
        return type;
    }

    public void setData (Data data) {
        this.data = data;
    }

    public void setType (ResponseType type) {
        this.type = type;
    }

    public static <Data> Response<Data> read(String input, ObjectMapper mapper, Class<Data> contentClass) throws IOException {
        // JavaType type = mapper.getTypeFactory().constructType(new TypeReference<Data>() {});
        JavaType type = mapper.getTypeFactory().constructParametricType(Response.class, contentClass);
        return mapper.readValue(input, type);
    }


//    public void setTimeInMillis(long millis) {
//        time.setTimeInMillis(millis);
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public Calendar getTime() {
//        return time;
//    }
//
//    public String getData() {
//        return data;
//    }
//
//    public ResponseType getType() {
//        return type;
//    }
}
