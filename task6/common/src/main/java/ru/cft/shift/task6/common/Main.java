package ru.cft.shift.task6.common;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Main {

    public static void main(String[] args) throws JsonProcessingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<String> names = Arrays.asList("one", "two", "three");
        System.out.println(names);
        Response<List> response = new Response(ResponseType.MESSAGE);
        //JsonNode node;
        Message mes = new Message("name", "text");

        String jsonmes = mapper.writeValueAsString(mes);
        System.out.println(jsonmes);
        mes = mapper.readValue(jsonmes, Message.class);
        System.out.println(mes);

        //node = mapper.valueToTree(names);
        response.setData(names);
        String json = mapper.writeValueAsString(response);
        System.out.println(json);
        // Response resp = mapper.readValue(json, new TypeReference<Response>() {});
        String types = mapper.readTree(json).get("type").asText();
        ResponseType type = ResponseType.MESSAGE;
        for (ResponseType stdType : ResponseType.values()) {
            if (stdType.name().equals(types)) {
                type = stdType;
            }
        }
        System.out.println(type);
        Response<? extends Object> resp = Response.read(json, mapper, List.class);
        // JsonParser parser = mapper.treeAsTokens(resp.getData());
        // Message out = mapper.treeToValue(resp.getData(), Message.class);
        //ArrayNode nodes = mapper.treeToValue(resp.getData(), ArrayNode.class);


        // System.out.println(out.getUserName() + mes.getText());
        System.out.println(resp.getData());
    }


}
