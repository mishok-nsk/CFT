package ru.cft.shift.task3.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task3.app.GameType;
import ru.cft.shift.task3.model.field.Field;
import ru.cft.shift.task3.model.gamelistener.*;
import ru.cft.shift.task3.model.highscores.HighScoreChecker;
import ru.cft.shift.task3.timer.GameTimer;


public class GameModel {
    private static final Logger logger = LoggerFactory.getLogger(GameModel.class);
    private Field field;
    private GameType gameType;
    private boolean isFieldFilled;
    private OpenCellListener openCellListener;
    private MarkCellListener markCellListener;
    private EndGameListener endGameListener;
    private HighScoreChecker highScoreChecker;
    private GameTimer timer;

    public GameModel(GameType gameType) {
        this.gameType = gameType;
        field = new Field(gameType.getRow(), gameType.getCol(), gameType.getBombCount(), this);
    }

    public void setOpenCellListener(OpenCellListener listener) {
        openCellListener = listener;
        field.setOpenCellListener(listener);
    }

    public void setMarkedCellListener(MarkCellListener markCellListener) {
        this.markCellListener = markCellListener;
        field.setMarkedCellListener(markCellListener);
    }

    public void setEndGameListener(EndGameListener endGameListener) {
        this.endGameListener = endGameListener;
    }

    public void setTimer(GameTimer timer) {
        this.timer = timer;
    }

    public void setGameType(GameType gameType) {
        logger.info("Устанавлием сложность игры: {}", gameType.getName());
        this.gameType = gameType;
        field = new Field(gameType.getRow(), gameType.getCol(), gameType.getBombCount(), this);
        field.setOpenCellListener(openCellListener);
        field.setMarkedCellListener(markCellListener);

        newGame();
    }

    public void setHighScoreChecker(HighScoreChecker hsh) {
        highScoreChecker = hsh;
    }

    public void newGame() {
        field.initCells();
        isFieldFilled = false;
        timer.reset();
    }

    public void openCells(int x, int y) {
        if (!isFieldFilled) {
            field.fillField(x, y);
            isFieldFilled = true;
            timer.start();
        }
        field.openCells(x, y);
    }

    public void openNeighboursWithCheck(int x, int y) {
        field.openNeighboursWithCheck(x, y);
    }

    public void markedCell(int x, int y) {
        field.markedCell(x, y);
    }

    public void endGame(boolean isWin) {
        logger.info("игра окончена.");
        timer.stop();
        if (isWin) {
            highScoreChecker.checkTimeRecord(timer.getTime(), gameType);
        }
        endGameListener.finishGame(isWin);
    }

    public void exitGame() {
        timer.stop();
    }
}
