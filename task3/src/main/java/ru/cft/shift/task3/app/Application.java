package ru.cft.shift.task3.app;

import ru.cft.shift.task3.controller.*;
import ru.cft.shift.task3.model.*;
import ru.cft.shift.task3.view.*;

public class Application {
    public static void main(String[] args) {
        GameType gameType = GameType.NOVICE;
        MainWindow mainWindow = new MainWindow();
        SettingsWindow settingsWindow = new SettingsWindow(mainWindow);
        HighScoresWindow highScoresWindow = new HighScoresWindow(mainWindow);

        mainWindow.setSettingsMenuAction(e -> settingsWindow.setVisible(true));
        mainWindow.setHighScoresMenuAction(e -> highScoresWindow.setVisible(true));
        mainWindow.setExitMenuAction(e -> mainWindow.dispose());

        GameModel gameModel = new GameModel(gameType);
        mainWindow.createGameField(gameType.getRow(), gameType.getCol());
        mainWindow.setBombsCount(gameType.getBombCount());
        mainWindow.setVisible(true);
        GameController gameController = new GameController(gameModel);

        Mediator mediator = new Mediator(gameController, gameModel, mainWindow);
        settingsWindow.setGameTypeListener(mediator::changeGameType);
    }
}
