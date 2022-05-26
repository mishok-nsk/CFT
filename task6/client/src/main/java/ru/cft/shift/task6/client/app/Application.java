package ru.cft.shift.task6.client.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task6.client.controller.ChatClientController;
import ru.cft.shift.task6.client.model.ChatClient;
import ru.cft.shift.task6.common.ChatProperties;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Приложение чат клиент запущено.");
        WindowsManager windowsManager = new WindowsManager();
        ChatClient chatClient = new ChatClient(ChatProperties.readPort());
        ChatClientController controller = new ChatClientController(chatClient);

        windowsManager.setChatClientController(controller);
        windowsManager.attachChatClient(chatClient);
        windowsManager.show();
    }
}