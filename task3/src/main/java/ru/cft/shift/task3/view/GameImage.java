package ru.cft.shift.task3.view;

import javax.swing.*;

public enum GameImage {
    CLOSED("cell.png"),
    MARKED("flag.png"),
    EMPTY("empty.png"),
    NUM_1("1.png"),
    NUM_2("2.png"),
    NUM_3("3.png"),
    NUM_4("4.png"),
    NUM_5("5.png"),
    NUM_6("6.png"),
    NUM_7("7.png"),
    NUM_8("8.png"),
    BOMB("mine.png"),
    TIMER("timer.png"),
    BOMB_ICON("mineImage.png"),
    ;

    private final String fileName;
    private ImageIcon imageIcon;

    GameImage(String fileName) {
        this.fileName = fileName;
    }

    public synchronized ImageIcon getImageIcon() {
        if (imageIcon == null) {
            imageIcon = new ImageIcon(ClassLoader.getSystemResource(fileName));
        }

        return imageIcon;
    }

    public static final GameImage[] IMAGE_INDEXES = {EMPTY, NUM_1, NUM_2, NUM_3, NUM_4, NUM_5, NUM_6, NUM_7, NUM_8, BOMB};
}
