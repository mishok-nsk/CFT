package ru.cft.shift.task6.client.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task6.client.controller.ChatClientController;
import ru.cft.shift.task6.client.model.ChatClient;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Приложение чат клиент запущено.");
        WindowsManager windowsManager = new WindowsManager();
        // MainWindow mainWindow = new MainWindow();
        // ConnectErrorWindow connectErrorWindow = new ConnectErrorWindow(mainWindow);
        ChatClient chatClient = new ChatClient(8090);
        ChatClientController controller = new ChatClientController(chatClient);

        windowsManager.setChatClientController(controller);
        windowsManager.attachChatClient(chatClient);
        windowsManager.show();
    }
}
