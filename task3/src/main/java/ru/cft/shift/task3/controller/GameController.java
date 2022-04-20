package ru.cft.shift.task3.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task3.view.ButtonType;
import ru.cft.shift.task3.view.CellEventListener;
import ru.cft.shift.task3.model.GameModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController implements ActionListener {
    private static final Logger logger = LoggerFactory.getLogger(GameController.class);
    private GameModel gameModel;

    public GameController(GameModel model) {
        gameModel = model;
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    // @Override
    public void onMouseClick(int x, int y, ButtonType buttonType) {
        if (buttonType == ButtonType.LEFT_BUTTON) {
            logger.info("Нажата кнопка [{}] [{}] {} кнопкой мыши.", x , y, ButtonType.LEFT_BUTTON);
            gameModel.openCells(x, y);
        } else if (buttonType == ButtonType.RIGHT_BUTTON) {
            logger.info("Нажата кнопка [{}] [{}] {} кнопкой мыши.", x , y, ButtonType.RIGHT_BUTTON);
            gameModel.flaggingCell(x, y);
        } else if (buttonType == ButtonType.MIDDLE_BUTTON) {
            logger.info("Нажата кнопка [{}] [{}] {} кнопкой мыши.", x , y, ButtonType.MIDDLE_BUTTON);
            gameModel.openNeighboursWithCheck(x, y);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        logger.info("Контроллер получил команду {} .", e.getActionCommand());
        if (e.getActionCommand().equals("New Game")) {
            gameModel.newGame();
        }
    }
}
