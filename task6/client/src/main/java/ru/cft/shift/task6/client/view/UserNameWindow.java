package ru.cft.shift.task6.client.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UserNameWindow extends JDialog {
    private List<ClientParameterListener> userNameListener = new ArrayList<>();
    private ActionListener exitListener;
    private JTextField userName;
    private JLabel label;

    public UserNameWindow(JFrame owner) {
        super(owner, "User name dialog", true);

        GridBagLayout layout = new GridBagLayout();
        Container contentPane = getContentPane();
        contentPane.setLayout(layout);

        label = createLabel(layout);
        contentPane.add(label);
        userName = createTextField(layout);
        contentPane.add(userName);
        contentPane.add(createOkButton(layout));
        contentPane.add(createExitButton(layout));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(300, 130));
        setResizable(false);
        pack();
    }

    public void addUserNameListener(ClientParameterListener listener) {
        userNameListener.add(listener);
    }

    public void setExitListener(ActionListener exitListener) {
        this.exitListener = exitListener;
    }

    public void showAuthorizationResponse(String response) {
        label.setText(response);
        showYourself();
    }

    public void showYourself() {
        setLocationRelativeTo(getOwner());
        setVisible(true);
    }

    private JLabel createLabel(GridBagLayout layout) {
        JLabel label = new JLabel("Enter user name:");
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
        GridBagConstraints gbc = new GridBagConstraints();
        textField.setPreferredSize(new Dimension(100, 20));
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.insets = new Insets(10, 0, 10, 0);
        layout.setConstraints(textField, gbc);
        return textField;
    }

    private JButton createOkButton(GridBagLayout layout) {
        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(100, 25));

        okButton.addActionListener(e -> {
            dispose();
            for (ClientParameterListener l : userNameListener) {
                l.setParameter(userName.getText());
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        // gbc.insets = new Insets(15, 0, 0, 0);
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
        gbc.insets = new Insets(0, 5, 0, 0);
        layout.setConstraints(exitButton, gbc);

        return exitButton;
    }
}
