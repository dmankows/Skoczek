import Configuration.LevelConfiguration;
import GamePlay.Board;
import GamePlay.Character;
import InGameGUI.Frame;
import InGameGUI.InformationPanel;
//import InGameGUI.KeyboardInput;
import InGameGUI.KeyboardInput;
import InGameGUI.Panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class Jumper implements Runnable {

    private Panel panel;
    private Character character;
    private boolean running;
    private KeyboardInput keyboard;

    private void tick() {
        character.setPositionX(keyboard.getPlayerX());
        character.setPositionY(keyboard.getPlayerY());
    }

    private void render() {
        panel.getPanel().repaint();
    }

    public Jumper() {
        LevelConfiguration levelConfiguration = new LevelConfiguration();// loads level configuration from the file
        character = new Character(levelConfiguration);
        Board board = new Board(levelConfiguration);

        Frame frame = new Frame();                                       // creates application's window
        panel = new Panel(levelConfiguration, character, board);         // creates panel and draw board and character
        SwingUtilities.invokeLater(() -> frame.getFrame().getContentPane().add(panel.getPanel(), BorderLayout.SOUTH));
        keyboard = new KeyboardInput(panel.getPanel(), character);
        SwingUtilities.invokeLater(() -> {

            panel.getPanel().setFocusable(true);
            panel.getPanel().requestFocusInWindow();
        });


        // InformationPanel infoText = new InformationPanel();              // creates JTextField...
        //frame.addComponent(infoText.getTextField(), BorderLayout.NORTH); // ... and sets it above JPanel

        frame.packFrame();                                               // pack and set visibility
        //panel.getPanel().addKeyListener(new KeyboardInput());
        start();
    }

    /**
     * The whole idea of our game loop is as follows:
     * - we want to achieve 60fps - this is the typical monitor refresh rate
     * - but what if our game renders at, let's say, 70fps? This will cause problems
     */
    @Override
    public void run() {
        final double TARGET_FPS = 100.0d; // we want to achieve 60 FPS refresh rate, more than this is unnecessary
        double unprocessed = 0.0d; // unprocessed time accumulated over the time
        final double TIME_BETWEEN_UPDATES = 1e9d / TARGET_FPS; // 1second / 60fps - how many ns it takes in-between refreshing frames
        long timer = System.currentTimeMillis();
        int fps = 0;
        int tps = 0; // ticks per second - how many times per second we update position of our character, etc.
        boolean canRender;
        long lastTime = System.nanoTime(); // time when the last rendering took place - in nanoseconds

        while (running) {
            long now = System.nanoTime();
            unprocessed += (now - lastTime) / TIME_BETWEEN_UPDATES; // delta time / (1e9/60fps)=60fps * delta (in seconds)=
            // = 60 frames / second * delta (in seconds) - tells us how many frames we skipped
            lastTime = now;
            // we cannot render fractal of a frame, hence we can only render when we've "accumulated" at least 1 frame
            if (unprocessed >= 1.0d) {
                tick();
                --unprocessed;
                ++tps;
                canRender = true;
            } else {                 // if we forget to set canRender to false, then the game will keep refreshing
                canRender = false;  // hundreds or even thousands times per second
            }
            try {
                Thread.sleep(10); // gives CPU a break
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }

            if (canRender) {
                render();
                ++fps;
            }

            if (System.currentTimeMillis() - 1000 > timer) { // 1000ms
                timer += 1000;
                fps = 0;
                tps = 0;
            }
        }
    }

    private void stop() {
        if (!running) {
            return;
        }
        running = false;
        System.exit(0);
    }

    private void start() {
        if (running) {
            return;
        }
        running = true;
        new Thread(this, "JumperMain-Thread").start();
    }
}
