package ru.cft.shift.task6.common;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Response<Data> {
    private ResponseType type;
    private Data data;

    public Response() {

    }

    public Response(ResponseType type) {
        this.type = type;
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
        JavaType type = mapper.getTypeFactory().constructParametricType(Response.class, contentClass);
        return mapper.readValue(input, type);
    }
}
