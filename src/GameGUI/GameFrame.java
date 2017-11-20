package GameGUI;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;


/**
 * This class represents the frame of our application
 */
public class GameFrame {

    private JFrame frame;

    /**
     * Creating JFrame and setting its parameters
     */
    public GameFrame() {
        frame = new JFrame();
        setFrameParameters(frame);
    }

    /**
     * Adding {@code component} to JFrame
     *
     * @param component component (e.g. JPanel) that we want to add to JFrame
     * @param position  the position of that {@code component}
     */
    public void addComponent(JComponent component, String position) {
        frame.add(component, position);
    }

    /**
     * After everything is set, pack the frame and set its visibility
     */
    public void showWindow() { // TODO consider changing the name of this method
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Setting frame's parameters
     *
     * @param frame Application's main frame
     */
    private void setFrameParameters(JFrame frame) {
        // getting the title of app's window
        ResourceBundle frameBundle = null;
        try {
            frameBundle = ResourceBundle.getBundle("Language/Frame/Frame");
        } catch (Exception e) {
            System.err.println("Can't load the frame's title from Language/Frame/Frame.properties!");
            e.printStackTrace();
            System.exit(1);
        }

        frame.setTitle(frameBundle.getString("frame_title"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);              // setting frame in the center of the screen
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("res/Icon.png"));
        frame.setResizable(false);
    }


}
