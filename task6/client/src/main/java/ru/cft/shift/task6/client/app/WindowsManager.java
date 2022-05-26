package ru.cft.shift.task6.client.app;

import ru.cft.shift.task6.client.controller.ChatClientController;
import ru.cft.shift.task6.client.model.ChatClient;
import ru.cft.shift.task6.client.view.MainWindow;
import ru.cft.shift.task6.client.view.ServerAddressWindow;
import ru.cft.shift.task6.client.view.UserNameWindow;

public class WindowsManager {
    MainWindow mainWindow;
    ServerAddressWindow serverAddressWindow;
    UserNameWindow userNameWindow;

    public WindowsManager() {
        mainWindow = new MainWindow();
        serverAddressWindow = new ServerAddressWindow(mainWindow);
        userNameWindow = new UserNameWindow(mainWindow);

        serverAddressWindow.setExitListener(e -> mainWindow.dispose());
        userNameWindow.setExitListener(e -> mainWindow.dispose());
        userNameWindow.addUserNameListener(mainWindow::setUserName);
    }

    public void setChatClientController(ChatClientController controller) {
        mainWindow.setExitListener(controller::chatExit);
        mainWindow.setMessageListener(controller::sendMessage);
        serverAddressWindow.setServerAddressListener(controller::hostEntered);
        userNameWindow.addUserNameListener(controller::userNameEntered);
    }

    public void attachChatClient(ChatClient chatClient) {
        chatClient.setConnectListener(isConnect -> {
            if (isConnect) {
                userNameWindow.showYourself();
            } else {
                serverAddressWindow.showConnectionError();
            }
        });
        chatClient.setAuthorizationListener(userNameWindow::showAuthorizationResponse);
        chatClient.setMessageListener(mainWindow);
    }

    public void show() {
        mainWindow.setVisible(true);
        serverAddressWindow.showYourself();
    }
}
