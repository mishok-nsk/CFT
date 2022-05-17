package ru.cft.shift.task6.client.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ServerAddressWindow extends JDialog {
    public static final String CONNECTION_ERROR = "Connection error! Retry?";
    public static final String DEFAULT_ADDRESS = "127.0.0.1";
    private ClientParameterListener serverAddressListener;
    private ActionListener exitListener;
    private JTextField serverAddress;
    private JLabel label;

    public ServerAddressWindow(JFrame owner) {
        super(owner, "Connection server address", true);

        GridBagLayout layout = new GridBagLayout();
        Container contentPane = getContentPane();
        contentPane.setLayout(layout);

        label = createLabel(layout);
        contentPane.add(label);
        serverAddress = createTextField(layout);
        contentPane.add(serverAddress);
        contentPane.add(createConnectButton(layout));
        contentPane.add(createExitButton(layout));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(300, 150));
        setResizable(false);
        pack();
    }

    public void setServerAddressListener(ClientParameterListener listener) {
        serverAddressListener = listener;
    }

    public void setExitListener(ActionListener exitListener) {
        this.exitListener = exitListener;
    }

    public void showConnectionError() {
        label.setText(CONNECTION_ERROR);
        // serverAddress.setText();
        showYourself();
    }

    public void showYourself() {
        setLocationRelativeTo(getOwner());
        setVisible(true);
    }

    private JLabel createLabel(GridBagLayout layout) {
        JLabel label = new JLabel("Enter server address.");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        layout.setConstraints(label, gbc);
        return label;
    }

    private JTextField createTextField(GridBagLayout layout) {
        JTextField textField = new JTextField();
        textField.setText(DEFAULT_ADDRESS);
        textField.setPreferredSize(new Dimension(100, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        layout.setConstraints(textField, gbc);
        return textField;
    }

    private JButton createConnectButton(GridBagLayout layout) {
        JButton okButton = new JButton("Connect");
        okButton.setPreferredSize(new Dimension(100, 25));

        okButton.addActionListener(e -> {
            dispose();
            if (serverAddressListener != null) {
                serverAddressListener.setParameter(serverAddress.getText());
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(10, 0, 0, 0);
        layout.setConstraints(okButton, gbc);

        return okButton;
    }

    private JButton createExitButton(GridBagLayout layout) {
        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(100, 25));

        exitButton.addActionListener(e -> {
            dispose();

            if (exitListener != null) {
                exitListener.actionPerformed(e);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(10, 5, 0, 0);
        layout.setConstraints(exitButton, gbc);

        return exitButton;
    }
}
