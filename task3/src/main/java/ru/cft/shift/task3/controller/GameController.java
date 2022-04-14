package ru.cft.shift.task3.controller;

import ru.cft.shift.task3.view.ButtonType;
import ru.cft.shift.task3.view.CellEventListener;
import ru.cft.shift.task3.model.GameModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController implements ActionListener {
    private GameModel gameModel;

    public GameController(GameModel model) {
        gameModel = model;
    }

    // @Override
    public void onMouseClick(int x, int y, ButtonType buttonType) {
        if (buttonType == ButtonType.LEFT_BUTTON) {
            gameModel.openCell(x, y);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameModel.newGame();
    }
}
