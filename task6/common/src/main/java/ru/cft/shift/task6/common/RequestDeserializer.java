package ru.cft.shift.task6.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Calendar;

public class RequestDeserializer extends StdDeserializer<Request> {
    public RequestDeserializer() {
        this(null);
    }
    public RequestDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Request deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        RequestType type = RequestType.MESSAGE;
        String typeName = node.get("requestType").asText();
        for (RequestType stdType : RequestType.values()) {
            if (stdType.name().equals(typeName)) {
                type = stdType;
            }
        }
        String data = node.get("data").asText();
        long time = node.get("time").asLong();
        // int timeValue = (Integer) node.get("timeValue").numberValue();
        Request request = new Request(type, data);
        // node.get("time").
        request.setTimeInMillis(time);
        return request;
    }
}
