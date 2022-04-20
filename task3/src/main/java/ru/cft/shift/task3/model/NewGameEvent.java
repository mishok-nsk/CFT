package ru.cft.shift.task3.model;

import ru.cft.shift.task3.view.GameType;

public class NewGameEvent extends GameEvent {

    private GameType gameType;

    public NewGameEvent(GameType newGameType) {
        super(GameEvent.NEW_GAME);
        gameType = newGameType;
    }

    public GameType getGameType() {
        return gameType;
    }
}
