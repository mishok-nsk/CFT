package ru.cft.shift.task3.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task3.model.HighScoresHandler;
import ru.cft.shift.task3.app.GameType;

public class HighScoresController {
    private static final Logger logger = LoggerFactory.getLogger(HighScoresController.class);
    private final HighScoresHandler highScoresHandler;

    public HighScoresController(HighScoresHandler highScoresHandler) {
        this.highScoresHandler = highScoresHandler;
    }

    public void userNameEntered(String name) {
        logger.info("Введено имя пользователя.");
        highScoresHandler.updateHighScore(name);
    }

    public void exitGame() {
        logger.info("Приложение заканчиваетработу.");
        highScoresHandler.writeHighScore();
    }

    public void endGame(int time, GameType gameType) {
        logger.info("игра закончена.");
        highScoresHandler.checkTimeRecord(time, gameType);
    }

}
