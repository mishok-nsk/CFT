package ru.cft.shift.task6.client.view;

import ru.cft.shift.task6.client.model.ConnectListener;
import ru.cft.shift.task6.client.model.MessageListener;
import ru.cft.shift.task6.common.Message;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class MainWindow extends JFrame implements MessageListener {
    private static final int MAX_MESSAGE_SIZE = 300;
    private static final String USER = "Пользователь ";
    private final Container contentPane;
    private JTextArea messageArea;
    private JTextArea chatArea;
    private JTextArea clientArea;
    private SendMessageListener sendMessageListener;
    private ExitListener exitListener;
    private ConnectionListener connectionListener;

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
    }

    public void setMessageListener(SendMessageListener listener) {
        sendMessageListener = listener;
    }

    public void setExitListener(ExitListener listener) {
        exitListener = listener;
    }

    public void setConnectionListener(ConnectionListener listener) {
        connectionListener = listener;
    }

    public void sendMessage() {
        String message = messageArea.getText();
        messageArea.setText("");
        sendMessageListener.getUserMessage(message);
    }

    public synchronized void addMessageToChat(String userName, Calendar time, String message) {
        chatArea.append(userName);
        chatArea.append("[" + time.getTime() + "]: ");
        chatArea.append(message);
        chatArea.append("\n");
    }

    public void setUserName(String userName) {
        this.setTitle("Chat client ( Nick: " + userName + " )");
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

        JPanel connectionPanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        connectionPanel.setLayout(layout);
        connectionPanel.add(createConnectButton(layout));
        connectionPanel.add(createDisconnectButton(layout));
        contentPane.add(connectionPanel, BorderLayout.NORTH);

        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());

        clientArea = new JTextArea();
        clientArea.setPreferredSize(new Dimension(120, 300));
        clientArea.setEnabled(false);
        clientArea.setDisabledTextColor(Color.BLACK);
        chatArea = new JTextArea();
        chatArea.setEnabled(false);
        chatArea.setDisabledTextColor(Color.BLACK);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane jScrollPane = new JScrollPane(chatArea);
        jScrollPane.setAutoscrolls(true);
        chatPanel.add(new JScrollPane(clientArea), BorderLayout.WEST);
        chatPanel.add(jScrollPane, BorderLayout.CENTER);
        contentPane.add(chatPanel, BorderLayout.CENTER);
    }

    private JButton createConnectButton(GridBagLayout layout) {
        JButton connectButton = new JButton("Connect");
        connectButton.setPreferredSize(new Dimension(100, 25));

        connectButton.addActionListener(e -> {
            if (connectionListener != null) {
                connectionListener.setConnect(true);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(10, 0, 10, 0);
        layout.setConstraints(connectButton, gbc);

        return connectButton;
    }

    private JButton createDisconnectButton(GridBagLayout layout) {
        JButton disconnectButton = new JButton("Disconnect");
        disconnectButton.setPreferredSize(new Dimension(100, 25));

        disconnectButton.addActionListener(e -> {
            if (connectionListener != null) {
                connectionListener.setConnect(false);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(10, 5, 10, 0);
        layout.setConstraints(disconnectButton, gbc);

        return disconnectButton;
    }

    @Override
    public void addNewMessage(Message message) {
        addMessageToChat(message.getUserName(), message.getTime(), message.getText());
    }

    @Override
    public void addUserMessage(String userName, String text) {
        chatArea.append(USER + userName + " ");
        chatArea.append(text);
        chatArea.append("\n");
    }

    @Override
    public void updateClientList(String text) {
        clientArea.setText(text);
    }
}
