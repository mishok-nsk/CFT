package ru.cft.shift.task2;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final boolean FILE = true;
    private static final Logger logger = LogManager.getLogger();
    private static String inputFile;
    private static String outputFile = " ";
    private static boolean out = false;

    private static void parseArguments(String[] arguments) {
        if (arguments.length == 0) error("Не указаны обязательные аргументы.");
        switch(arguments[0]) {
            case "-c" :
                break;
            case "-f" :
                out = FILE;
                break;
            default :
                error("Не указан параметр вывода результата.");
        }

        if (arguments.length < 2) error("Не указано имя входного файла с данными.");
        inputFile = arguments[1];

        if (out == FILE && arguments.length < 3) error("Не указано имя выходного файла.");

        if (out == FILE) outputFile = arguments[2];
    }

    private static void error(String message) {
        if (message != null) {
            logger.log(Level.ERROR, message);
        }
        System.out.println("Ошибка в параметрах.");
        System.out.println("usage: Main (-c|-f) <input.txt> <output.txt>");
        logger.info("Завершение работы.");
        System.exit(0);
    }

    public static void main(String[] args) {
        logger.log(Level.INFO, "Приложение запущено.");
        parseArguments(args);
        ReadFigureFromFile rf;
        try {
            logger.info("Чтение данных фигуры из файла.");
            rf = new ReadFigureFromFile(inputFile);
        } catch (Exception e) {
            error(e.getMessage());
            return;
        }

        Figure figure;
        try {
            logger.info("Создаем фигуру.");
            figure = FigureCreator.create(rf.getTypeFigure(), rf.getParams());
        }
        catch (Exception e) {
            logger.info("Фигура не создана: " + e.getMessage());
            logger.info("Завершение работы.");
            return;
        }

        try {
            logger.info("Вывод параметров фигуры " + figure.getName() + ".");
            if (out == FILE) {
                PrintFigure.printToFile(figure, outputFile);
            }
            else {
                PrintFigure.printToConsole(figure);
            }
        }
        catch(Exception e) {
            logger.info(e.getMessage());
            logger.info("Завершение работы.");
        }
    }
}
