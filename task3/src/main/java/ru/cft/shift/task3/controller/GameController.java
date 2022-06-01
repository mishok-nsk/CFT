package ru.cft.shift.task3.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task3.view.ButtonType;
import ru.cft.shift.task3.model.GameModel;
import ru.cft.shift.task3.app.GameType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController implements ActionListener {
    private static final Logger logger = LoggerFactory.getLogger(GameController.class);
    private final GameModel gameModel;

    public GameController(GameModel model) {
        gameModel = model;
    }

    public void onMouseClick(int x, int y, ButtonType buttonType) {
        switch (buttonType) {
            case LEFT_BUTTON -> {
                logger.debug("Нажата кнопка [{}] [{}] {} кнопкой мыши.", x, y, ButtonType.LEFT_BUTTON);
                gameModel.openCells(x, y);
            }
            case RIGHT_BUTTON -> {
                logger.debug("Нажата кнопка [{}] [{}] {} кнопкой мыши.", x, y, ButtonType.RIGHT_BUTTON);
                gameModel.markedCell(x, y);
            }
            case MIDDLE_BUTTON -> {
                logger.debug("Нажата кнопка [{}] [{}] {} кнопкой мыши.", x, y, ButtonType.MIDDLE_BUTTON);
                gameModel.openNeighboursWithCheck(x, y);
            }
        }
    }

    public void changeGameType(GameType gameType) {
        logger.info("изменены настройки игры. Выбран тип: {}.", gameType.getName());
        gameModel.setGameType(gameType);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logger.info("Контроллер игровой модели получил команду {} .", e.getActionCommand());
        if (e.getActionCommand().equals("New Game")) {
            gameModel.newGame();
        }
    }

    public void exitGame() {
        logger.info("Приложение заканчиваетработу.");
        gameModel.exitGame();
    }
}
