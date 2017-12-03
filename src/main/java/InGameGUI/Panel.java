package InGameGUI;

import Configuration.*;
import GamePlay.Board;
import GamePlay.Character;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

/**
 * Class which represents part of window where the game will take place
 */
public class Panel{

    private JPanel panel;

    public Panel(final LevelConfiguration levelConfiguration, final Character character, final Board board) {
        int gamePanelWidth = levelConfiguration.getGamePanelWidth();
        int gamePanelHeight = levelConfiguration.getGamePanelHeight();
        panel = new JPanel(true) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                board.drawBoard(g);
                character.drawCharacter(g);
            }
        };

        SwingUtilities.invokeLater(() -> {
            panel.setBackground(levelConfiguration.getBackgroundColor());
            panel.setLayout(new FlowLayout());
            panel.setPreferredSize(new Dimension(gamePanelWidth, gamePanelHeight));
            panel.setBorder(BorderFactory.createEmptyBorder());
            panel.setFocusable(true);

        });
    }




    /* GETTERS */

    public JPanel getPanel() {
        return panel;
    }
}
