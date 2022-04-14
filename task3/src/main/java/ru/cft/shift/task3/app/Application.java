package ru.cft.shift.task3.app;

import ru.cft.shift.task3.controller.GameController;
import ru.cft.shift.task3.model.GameModel;
import ru.cft.shift.task3.view.*;

public class Application {
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        SettingsWindow settingsWindow = new SettingsWindow(mainWindow);
        HighScoresWindow highScoresWindow = new HighScoresWindow(mainWindow);
        GameModel gameModel = new GameModel(10, 10);
        GameController gameController = new GameController(gameModel);
        mainWindow.setNewGameMenuAction(gameController);
        mainWindow.setSettingsMenuAction(e -> settingsWindow.setVisible(true));
        mainWindow.setHighScoresMenuAction(e -> highScoresWindow.setVisible(true));
        mainWindow.setExitMenuAction(e -> mainWindow.dispose());
        mainWindow.setCellListener((x, y, buttonType) -> gameController.onMouseClick(x, y, buttonType));

        gameModel.setNewGameListener((row, col) -> mainWindow.createGameField(row, col));
        gameModel.setOpenCellListener((x, y, bomb) -> mainWindow.setCellImage(x, y, GameImage.EMPTY));
        mainWindow.createGameField(10, 10);
        mainWindow.setVisible(true);

        // TODO: There is a sample code below, remove it after try

        mainWindow.setTimerValue(145);
        mainWindow.setBombsCount(45);
        mainWindow.setCellImage(0, 0, GameImage.EMPTY);
        mainWindow.setCellImage(0, 1, GameImage.CLOSED);
        mainWindow.setCellImage(0, 2, GameImage.MARKED);
        mainWindow.setCellImage(0, 3, GameImage.BOMB);
        mainWindow.setCellImage(1, 0, GameImage.NUM_1);
        mainWindow.setCellImage(1, 1, GameImage.NUM_2);
        mainWindow.setCellImage(1, 2, GameImage.NUM_3);
        mainWindow.setCellImage(1, 3, GameImage.NUM_4);
        mainWindow.setCellImage(1, 4, GameImage.NUM_5);
        mainWindow.setCellImage(1, 5, GameImage.NUM_6);
        mainWindow.setCellImage(1, 6, GameImage.NUM_7);
        mainWindow.setCellImage(1, 7, GameImage.NUM_8);
    }
}
