package InGameGUI;

import GamePlay.Character;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyboardInput {
    private Map<Direction, Boolean> directionMap = new HashMap<>();
    private int playerX;
    private int playerY;
    private static final int STEP = 2;
    private static final int TIMER_DELAY = 6;

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    enum Direction {

        UP(KeyEvent.VK_UP, 0, -1), DOWN(KeyEvent.VK_DOWN, 0, 1),
        LEFT(KeyEvent.VK_LEFT, -1, 0), RIGHT(KeyEvent.VK_RIGHT, 1, 0);
        private int keyCode;
        private int xDirection;
        private int yDirection;

        Direction(int keyCode, int xDirection, int yDirection) {
            this.keyCode = keyCode;
            this.xDirection = xDirection;
            this.yDirection = yDirection;
        }

        public int getKeyCode() {
            return keyCode;
        }

        public int getXDirection() {
            return xDirection;
        }

        public int getYDirection() {
            return yDirection;
        }
    }

    public KeyboardInput(JPanel panel, Character character) {
        playerX = character.getPositionX();
        playerY = character.getPositionY();
        for (Direction direction : Direction.values()) {
            directionMap.put(direction, false);
        }
        setKeyBindings(panel);
        SwingUtilities.invokeLater(() -> {

            Timer timer = new Timer(TIMER_DELAY, new TimerListener());
            timer.start();
        });
    }

    private void setKeyBindings(JPanel panel) {
        SwingUtilities.invokeLater(() -> {

            InputMap inMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actMap = panel.getActionMap();
            for (final Direction direction : Direction.values()) {
                KeyStroke pressed = KeyStroke.getKeyStroke(direction.getKeyCode(), 0, false);
                KeyStroke released = KeyStroke.getKeyStroke(direction.getKeyCode(), 0, true);
                inMap.put(pressed, direction.toString() + "pressed");
                inMap.put(released, direction.toString() + "released");
                actMap.put(direction.toString() + "pressed", new AbstractAction() {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        directionMap.put(direction, true);
                    }
                });
                actMap.put(direction.toString() + "released", new AbstractAction() {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        directionMap.put(direction, false);
                    }
                });
            }
        });

    }

    private class TimerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (Direction direction : Direction.values()) {
                if (directionMap.get(direction)) {
                    playerX += STEP * direction.getXDirection();
                    playerY += STEP * direction.getYDirection();
                }
            }
        }
    }


}
