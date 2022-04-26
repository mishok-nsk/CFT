package ru.cft.shift.task3.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = HighScoreDataDeserializer.class)
public class HighScoreData {
    private final String winnerName;
    private final int timeValue;

    public HighScoreData(String winnerName, int timeValue) {
        this.winnerName = winnerName;
        this.timeValue = timeValue;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public int getTimeValue() {
        return timeValue;
    }
}
