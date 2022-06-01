package ru.cft.shift.task3.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task3.model.highscores.HighScoresHandler;

public class HighScoresController {
    private static final Logger logger = LoggerFactory.getLogger(HighScoresController.class);
    private final HighScoresHandler highScoresHandler;

    public HighScoresController(HighScoresHandler highScoresHandler) {
        this.highScoresHandler = highScoresHandler;
    }

    public void setRecordName(String name) {
        logger.info("Получено имя нового рекордсмена: {}", name);
        highScoresHandler.updateHighScore(name);
    }

    public void exitGame() {
        logger.info("Приложение заканчивает работу.");
        highScoresHandler.writeHighScore();
    }

}
