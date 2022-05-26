package ru.cft.shift.task6.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
        ObjectMapper mapper = new ObjectMapper();
        ResponseType type = ResponseType.MESSAGE;
        String typeName = node.get("type").asText();
        for (ResponseType stdType : ResponseType.values()) {
            if (stdType.name().equals(typeName)) {
                type = stdType;
            }
        }
        // ObjectMapper mapper = new ObjectMapper();
        Response resp = null;
//        switch (type) {
//            case USER_IN_LIST -> {
//                Response response = Response.<Set<String>>read(jp, mapper);
//            }
//            case USER_IN, USER_OUT, AUTHORIZATION_ERROR, AUTHORIZATION_OK -> {
//                // response = Response.<String>read(jp, mapper);
//                Response<String> response = new Response<>(type);
//                response.setData(node.get("data").asText());
//            }
//            case MESSAGE -> {
//                Response<Message> response = new Response<>(type);
//                Message message = mapper.readValue(node.get("data").asText(), Message.class);
//                response.setData(message);
//            }
//            default -> throw new IOException("Неизвестный тип сообщения");
//        }
        // resp = response;
        // String userName = node.get("userName").asText();
        // String data = node.get("data").asText();
        // long time = node.get("time").asLong();
        // int timeValue = (Integer) node.get("timeValue").numberValue();
        // Response response = new Response(type, userName, data);
        // response.setTimeInMillis(time);
        // node.get("time").
        return resp;
    }
}
