package ru.cft.shift.task3.app;

import ru.cft.shift.task3.controller.*;
import ru.cft.shift.task3.model.*;
import ru.cft.shift.task3.model.highscores.HighScoresHandler;
import ru.cft.shift.task3.timer.MyTimer;

import java.util.Properties;

public class Application {
    public static void main(String[] args) {
        Properties properties = MyProperties.get();
        GameType gameType = GameType.NOVICE;

        WindowsManager windowsManager = new WindowsManager(gameType);

        HighScoresHandler highScoresHandler = new HighScoresHandler(properties.getProperty("highScoreFileName"));
        GameModel gameModel = new GameModel(gameType);
        GameController gameController = new GameController(gameModel);
        HighScoresController highScoresController = new HighScoresController(highScoresHandler);
        gameModel.setHighScoreChecker(highScoresHandler);

        MyTimer timer = new MyTimer();
        windowsManager.attachTimer(timer);
        gameModel.setTimer(timer);

        windowsManager.setNewGameListener(gameController);
        windowsManager.setCellListener(gameController::onMouseClick);
        windowsManager.setGameTypeListener(gameController::changeGameType);
        windowsManager.addGameExitListener(gameController::exitGame);
        windowsManager.addGameExitListener(highScoresController::exitGame);
        windowsManager.setNameListener(highScoresController::setRecordName);

        windowsManager.attachHighScoreHandler(highScoresHandler);
        windowsManager.attachGameModel(gameModel);

        windowsManager.setVisibleMainWindow();
    }
}
