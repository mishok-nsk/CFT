package ru.cft.shift.task3.model;

// import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;

import java.io.IOException;

public class HighScoreDataDeserializer extends StdDeserializer<HighScoreData> {
    public HighScoreDataDeserializer() {
        this(null);
    }
    public HighScoreDataDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public HighScoreData deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonParseException {
        JsonNode node = jp.getCodec().readTree(jp);
        String winnerName = node.get("winnerName").asText();
        int timeValue = (Integer) ((IntNode) node.get("timeValue")).numberValue();
        return new HighScoreData(winnerName, timeValue);
    }
}
