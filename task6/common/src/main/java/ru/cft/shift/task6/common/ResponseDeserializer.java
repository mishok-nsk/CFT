package ru.cft.shift.task6.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class ResponseDeserializer extends StdDeserializer {
    public ResponseDeserializer() {
        this(null);
    }
    public ResponseDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Response deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        ResponseType type = ResponseType.MESSAGE;
        String typeName = node.get("type").asText();
        for (ResponseType stdType : ResponseType.values()) {
            if (stdType.name().equals(typeName)) {
                type = stdType;
            }
        }
        String userName = node.get("userName").asText();
        String data = node.get("data").asText();
        long time = node.get("time").asLong();
        // int timeValue = (Integer) node.get("timeValue").numberValue();
        Response response = new Response(type, userName, data);
        response.setTimeInMillis(time);
        // node.get("time").
        return response;
    }
}
