package ru.cft.shift.task6.client.view;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

public class MainWindow extends JFrame {
    private static final int MAX_MESSAGE_SIZE = 300;
    public static final String SYSTEM_ENCODING = "windows-1251";
    private final Container contentPane;
    private JTextArea messageArea;
    private JTextArea chatArea;
    private JTextArea clientArea;
    private SendMessageListener sendMessageListener;
    private ExitListener exitListener;
    // private final GridLayout mainLayout;

    public MainWindow() {
        super("Chat client");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(500, 330);

        contentPane = getContentPane();
        BorderLayout mainLayout = new BorderLayout();
        contentPane.setLayout(mainLayout);
        contentPane.setBackground(new Color(144, 158, 184));

        createContent();
        pack();
        //setVisible(true);
    }

    public void setMessageListener(SendMessageListener listener) {
        sendMessageListener = listener;
    }

    public void setExitListener(ExitListener listener) {
        exitListener = listener;
    }

    public void sendMessage() {
        String message = messageArea.getText();
        messageArea.setText("");
        // addMessageToChat(message);
        sendMessageListener.getUserMessage(message);
    }

    public void addMessageToChat(String userName, Calendar time, String message) {
        try {
            String user = new String(userName.getBytes(SYSTEM_ENCODING), "UTF-8");
            chatArea.append(user);
            chatArea.append("[" + time.getTime() + "]: ");
            chatArea.append(message);
            chatArea.append("\n");
        } catch (Exception e) {

        }
    }

    public void setUserName(String userName) {
        clientArea.append("");
        clientArea.append(userName + "(Вы)\n");
    }

    public void dispose() {
        exitListener.chatClose();
        super.dispose();
    }

    private void createContent() {
        JPanel messagePanel = new JPanel();
        messageArea = new JTextArea(3, 50);
        messagePanel.setLayout(new BorderLayout());
        messagePanel.setMaximumSize(new Dimension(4000, 20));
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setDocument(new LimitedLinesDocument(MAX_MESSAGE_SIZE));
        Button sendMessage = new Button("Send");
        sendMessage.addActionListener(e -> this.sendMessage());

        messagePanel.add(new JScrollPane(messageArea), BorderLayout.CENTER);
        messagePanel.add(sendMessage, BorderLayout.EAST);
        contentPane.add(messagePanel, BorderLayout.SOUTH);
        messageArea.getText();

        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());

        clientArea = new JTextArea();
        clientArea.setPreferredSize(new Dimension(200, 300));
        chatArea = new JTextArea();
        Font font = new Font("Verdana", Font.PLAIN, 12);
        chatArea.setFont(font);
        chatPanel.add(new JScrollPane(clientArea), BorderLayout.WEST);
        chatPanel.add(new JScrollPane(chatArea), BorderLayout.CENTER);
        contentPane.add(chatPanel, BorderLayout.CENTER);
    }
}
