package ru.cft.shift.task3.app;

import ru.cft.shift.task3.model.GameModel;
import ru.cft.shift.task3.model.highscores.HighScoresHandler;
import ru.cft.shift.task3.timer.GameTimer;
import ru.cft.shift.task3.view.*;

import java.awt.event.ActionListener;

public class WindowsManager {
    private final MainWindow mainWindow;
    private final SettingsWindow settingsWindow;
    private final HighScoresWindow highScoresWindow;
    private final RecordsWindow recordsWindow;
    private final WinWindow winWindow;
    private final LoseWindow loseWindow;

    public WindowsManager(GameType gameType) {
        mainWindow = new MainWindow();
        settingsWindow = new SettingsWindow(mainWindow);
        highScoresWindow = new HighScoresWindow(mainWindow);
        recordsWindow = new RecordsWindow(mainWindow);
        winWindow = new WinWindow(mainWindow);
        loseWindow = new LoseWindow(mainWindow);

        mainWindow.createGameField(gameType);
        mainWindow.setSettingsMenuAction(e -> settingsWindow.showYourself());
        mainWindow.setHighScoresMenuAction(e -> highScoresWindow.showYourself());
        mainWindow.setExitMenuAction(e -> mainWindow.dispose());

        settingsWindow.setGameTypeListener(mainWindow::setGameType);

        loseWindow.setExitListener(e -> mainWindow.dispose());
        winWindow.setExitListener(e -> mainWindow.dispose());
        loseWindow.addNewGameListener(e -> mainWindow.clearGameField());
        winWindow.addNewGameListener(e -> mainWindow.clearGameField());
    }

    public void setNewGameListener(ActionListener listener) {
        mainWindow.setNewGameMenuAction(listener);
        loseWindow.addNewGameListener(listener);
        winWindow.addNewGameListener(listener);
    }

    public void setCellListener(CellEventListener listener) {
        mainWindow.setCellListener(listener);
    }

    public void addGameExitListener(GameExitListener listener) {
        mainWindow.addGameExitListener(listener);
    }

    public void setGameTypeListener(GameTypeListener listener) {
        settingsWindow.setGameTypeListener(listener);
    }

    public void setNameListener(RecordNameListener listener) {
        recordsWindow.setNameListener(listener);
    }

    public void attachTimer(GameTimer timer) {
        timer.setTimerListener(mainWindow::setTimerValue);
    }

    public void attachHighScoreHandler(HighScoresHandler highScoresHandler) {
        highScoresHandler.setHighScoreListener(highScoresWindow);
        highScoresHandler.setUpdateRecordListener(recordsWindow::showYourself);
        highScoresHandler.readHighScoreData();
    }

    public void attachGameModel(GameModel gameModel) {
        gameModel.setOpenCellListener((x, y, value) -> mainWindow.setCellImage(x, y, GameImage.IMAGE_INDEXES[value]));
        gameModel.setMarkedCellListener((x, y, bombCount, isMarked) -> {
            mainWindow.setCellImage(x, y, isMarked ? GameImage.MARKED : GameImage.CLOSED);
            mainWindow.setBombsCount(bombCount);
        });
        gameModel.setEndGameListener((isWin) -> {
            if (isWin) {
                winWindow.showYourself();
            } else {
                loseWindow.showYourself();
            }
        });
    }

    public void setVisibleMainWindow() {
        mainWindow.setVisible(true);
    }

}
