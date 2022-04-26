package ru.cft.shift.task3.app;

import ru.cft.shift.task3.controller.*;
import ru.cft.shift.task3.model.*;
import ru.cft.shift.task3.view.*;

import java.util.Properties;

public class Application {
    public static void main(String[] args) {
        Properties properties = MyProperties.get();
        GameType gameType = GameType.NOVICE;

        MainWindow mainWindow = new MainWindow();
        SettingsWindow settingsWindow = new SettingsWindow(mainWindow);
        HighScoresWindow highScoresWindow = new HighScoresWindow(mainWindow);
        RecordsWindow recordsWindow = new RecordsWindow(mainWindow);
        WinWindow winWindow = new WinWindow(mainWindow);
        LoseWindow loseWindow = new LoseWindow(mainWindow);

        GameModel gameModel = new GameModel(gameType);
        GameController gameController = new GameController(gameModel);
        HighScoresHandler highScoresHandler = new HighScoresHandler(properties.getProperty("highScoreFileName"));
        HighScoresController highScoreController = new HighScoresController(highScoresHandler);


        mainWindow.setGameType(gameType);
        mainWindow.setSettingsMenuAction(e -> settingsWindow.setVisible(true));
        mainWindow.setHighScoresMenuAction(e -> highScoresWindow.setVisible(true));
        mainWindow.setExitMenuAction(e -> mainWindow.dispose());
        mainWindow.setNewGameMenuAction(gameController);
        mainWindow.setCellListener(gameController::onMouseClick);
        mainWindow.setGameExitListener(highScoreController::exitGame);
        mainWindow.setTimerListener(highScoreController::endGame);

        settingsWindow.setGameTypeListener(gameController::changeGameType);
        loseWindow.setExitListener(e -> mainWindow.dispose());
        loseWindow.setNewGameListener(gameController);
        winWindow.setExitListener(e -> mainWindow.dispose());
        winWindow.setNewGameListener(gameController);
        recordsWindow.setNameListener(highScoreController::userNameEntered);

        highScoresHandler.setHighScoreListener(highScoresWindow);
        highScoresHandler.readHighScoreData();
        highScoresHandler.setUpdateRecordListener(() -> recordsWindow.setVisible(true));
        recordsWindow.setNameListener(highScoreController::userNameEntered);

        gameModel.setGameListener( e -> {
            switch (e.getEvent()) {
                case GameEvent.NEW_GAME -> {
                    GameType gt = ((NewGameEvent) e).getGameType();
                    mainWindow.newGame(gt);
                }
                case GameEvent.START_GAME -> mainWindow.startTimer();
                case GameEvent.OPEN_CELL -> {
                    OpenCellEvent oce = (OpenCellEvent) e;
                    mainWindow.setCellImage(oce.getX(), oce.getY(), GameImage.IMAGE_INDEXES[oce.getValue()]);
                }
                case GameEvent.MARKED_CELL -> {
                    MarkedCellEvent mce = (MarkedCellEvent) e;
                    mainWindow.setCellImage(mce.getX(), mce.getY(), GameImage.MARKED);
                    mainWindow.setBombsCount(mce.getBombCount());
                }
                case GameEvent.UNMARKED_CELL -> {
                    MarkedCellEvent mce = (MarkedCellEvent) e;
                    mainWindow.setCellImage(mce.getX(), mce.getY(), GameImage.CLOSED);
                    mainWindow.setBombsCount(mce.getBombCount());
                }
                case GameEvent.YOU_WIN -> {
                    mainWindow.stopTimer(true);
                    winWindow.setVisible(true);
                }
                case GameEvent.YOU_LOSE -> {
                    mainWindow.stopTimer(false);
                    loseWindow.setVisible(true);
                }
            }
        });
        mainWindow.setVisible(true);
    }
}
