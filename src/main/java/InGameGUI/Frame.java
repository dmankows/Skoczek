package InGameGUI;

import Configuration.Bundle;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;


/**
 * This class represents the frame of our application
 */
public class Frame {

    /**
     * Our JFrame - application's main window
     */
    private JFrame frame;

    /**
     * Creating JFrame and setting its parameters
     */
    public Frame() {
        ResourceBundle frameBundle = Bundle.loadBundle("Language/Frame/Frame");
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame();
            setFrameParameters(frame, frameBundle);
        });
    }

    /**
     * Adding {@code component} to JFrame
     *
     * @param component component (e.g. JPanel) that we want to add to JFrame
     * @param position  the position of that {@code component}
     */
    public void addComponent(JComponent component, String position) {
        SwingUtilities.invokeLater(() -> frame.add(component, position));
    }

    /**
     * After everything is set, pack the frame and set its visibility
     */
    public void packFrame() {
        SwingUtilities.invokeLater(() -> {
            frame.pack();
            frame.setVisible(true);
        });
    }

    /**
     * Setting frame's parameters
     *
     * @param frame Application's main frame
     */
    private void setFrameParameters(JFrame frame, ResourceBundle frameBundle) {
        frame.setTitle(frameBundle.getString("frame_title"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);              // setting frame in the center of the screen
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("Icon.png"));
        frame.setResizable(false);
    }

    public JFrame getFrame(){
        return frame;
    }
}
