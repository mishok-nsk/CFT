package ru.cft.shift.task2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger();
    private static String inputFile;
    private static String outputFile;
    private static boolean isOutToFile = false;

    private static void parseArguments(String[] arguments) throws FigureParseException {
        if (arguments.length == 0) {
            throw new FigureParseException("Не указаны обязательные аргументы.");
        }
        switch(arguments[0]) {
            case "-c" :
                break;
            case "-f" :
                isOutToFile = true;
                break;
            default :
                throw new FigureParseException("Не указан параметр вывода результата.");
        }

        if (arguments.length < 2) {
            throw new FigureParseException("Не указано имя входного файла с данными.");
        }
        inputFile = arguments[1];

        if (isOutToFile && arguments.length < 3) {
            throw new FigureParseException("Не указано имя выходного файла.");
        }

        if (isOutToFile) outputFile = arguments[2];
    }

    public static void main(String[] args) {
        logger.info("Приложение запущено.");
        try {
            parseArguments(args);
            logger.info("Чтение данных фигуры из файла.");
            ReadFigureFromFile rf;
            rf = new ReadFigureFromFile(inputFile);
            logger.info("Создаем фигуру.");
            Figure figure;
            figure = FigureCreator.create(rf.getTypeFigure(), rf.getParams());
            logger.info("Вывод параметров фигуры {} .", figure.getName());
            if (isOutToFile) {
                PrintFigure.printToFile(figure, outputFile);
            } else {
                PrintFigure.printToConsole(figure);
            }
        } catch (FigureParseException e) {
            logger.error(e.getMessage());
            logger.error("usage: Main (-c|-f) <input.txt> <output.txt>");
        } catch (FigureReadException e) {
            logger.error("Ошибка чтения: {}", e.getMessage());
        } catch (FigureCreateException e) {
            logger.error("Фигура не создана: {}", e.getMessage());
        } catch(Exception e) {
            logger.error(e.getMessage());
        } finally {
            logger.info("Завершение работы.");
        }
    }
}

class FigureParseException extends Exception {

    public FigureParseException(String message) {
        super(message);
    }
}

class FigureCreateException extends Exception {

    public FigureCreateException(String message) {
        super(message);
    }
}
class FigureReadException extends Exception {

    public FigureReadException(String message) {
        super(message);
    }
}