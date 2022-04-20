package ru.cft.shift.task3.controller;

import ru.cft.shift.task3.model.*;
import ru.cft.shift.task3.view.*;

public class Mediator {
    private final GameController controller;
    private final MainWindow mainWindow;
    private LoseWindow loseWindow;
    private WinWindow winWindow;


    public Mediator(GameController controller, GameModel model, MainWindow mainWindow) {
        this.controller = controller;
        this.mainWindow = mainWindow;
        connectVC();
        setModelListener(model);
    }

    public void changeGameType(GameType gameType) {
        GameModel model = new GameModel(gameType);
        controller.setGameModel(model);
        setModelListener(model);
        model.newGame();
    }

    private void connectVC() {
        mainWindow.setNewGameMenuAction(controller);
        mainWindow.setCellListener(controller::onMouseClick);
        loseWindow = new LoseWindow(mainWindow);
        winWindow = new WinWindow(mainWindow);
        loseWindow.setExitListener(e -> mainWindow.dispose());
        loseWindow.setNewGameListener(controller);

        winWindow.setExitListener(e -> mainWindow.dispose());
        winWindow.setNewGameListener(controller);

    }

    private void setModelListener(GameModel gameModel) {
        gameModel.setGameListener( e -> {
            switch (e.getEvent()) {
                case GameEvent.NEW_GAME -> {
                    GameType gameType = ((NewGameEvent) e).getGameType();
                    mainWindow.createGameField(gameType.getRow(), gameType.getCol());
                    mainWindow.setBombsCount(gameType.getBombCount());
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
                    mainWindow.stopTimer();
                    winWindow.setVisible(true);
                }
                case GameEvent.YOU_LOSE -> {
                    mainWindow.stopTimer();
                    loseWindow.setVisible(true);
                }
            }
        });
    }
}
