package ru.cft.shift.task3.model.highscores;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = HighScoreDataDeserializer.class)
public record HighScoreData(String winnerName, int timeValue) {

    public String getWinnerName() {
        return winnerName;
    }

    public int getTimeValue() {
        return timeValue;
    }
}
