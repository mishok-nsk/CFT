package ru.cft.shift.task3.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task3.controller.*;
import ru.cft.shift.task3.model.*;
import ru.cft.shift.task3.model.highscores.HighScoresHandler;
import ru.cft.shift.task3.timer.GameTimer;

import java.util.Properties;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Приложение запущено.");
        Properties properties = MyProperties.get();
        GameType gameType = GameType.NOVICE;

        WindowsManager windowsManager = new WindowsManager(gameType);

        HighScoresHandler highScoresHandler = new HighScoresHandler(properties.getProperty("highScoreFileName"));
        GameModel gameModel = new GameModel(gameType);
        GameController gameController = new GameController(gameModel);
        HighScoresController highScoresController = new HighScoresController(highScoresHandler);
        gameModel.setHighScoreChecker(highScoresHandler);

        GameTimer timer = new GameTimer();
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
