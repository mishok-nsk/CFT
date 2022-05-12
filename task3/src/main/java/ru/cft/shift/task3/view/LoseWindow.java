package ru.cft.shift.task3.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class LoseWindow extends JDialog {
    private final List<ActionListener> newGameListener;
    private ActionListener exitListener;

    public LoseWindow(JFrame owner) {
        super(owner, "Lose", true);

        GridBagLayout layout = new GridBagLayout();
        Container contentPane = getContentPane();
        contentPane.setLayout(layout);

        contentPane.add(createLoseLabel(layout));
        contentPane.add(createNewGameButton(layout));
        contentPane.add(createExitButton(layout));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(300, 130));
        setResizable(false);
        pack();
        // setLocationRelativeTo(null);
        // setVisible(false);

        newGameListener = new ArrayList<>();
    }

    public void addNewGameListener(ActionListener newGameListener) {
        this.newGameListener.add(newGameListener);
    }

    public void setExitListener(ActionListener exitListener) {
        this.exitListener = exitListener;
    }

    public void showYourself() {
        setLocationRelativeTo(getOwner());
        setVisible(true);
    }

    private JLabel createLoseLabel(GridBagLayout layout) {
        JLabel label = new JLabel("You lose!");
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

    private JButton createNewGameButton(GridBagLayout layout) {
        JButton newGameButton = new JButton("New Game");
        newGameButton.setPreferredSize(new Dimension(100, 25));

        newGameButton.addActionListener(e -> {
            dispose();

            for (ActionListener l : newGameListener) {
                l.actionPerformed(e);
            }
//            if (newGameListener != null) {
//                newGameListener.actionPerformed(e);
//            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(15, 0, 0, 0);
        layout.setConstraints(newGameButton, gbc);

        return newGameButton;
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
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(15, 5, 0, 0);
        layout.setConstraints(exitButton, gbc);

        return exitButton;
    }
}
