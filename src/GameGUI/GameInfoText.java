package GameGUI;

import Configuration.AppConfiguration;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

import static GameGUI.Constants.TEXT_FIELD_FONT_SIZE;
import static GameGUI.Constants.TEXT_FIELD_HEIGHT;

/**
 * This class represents JTextField with the information about lives, time left, etc.
 */
public class GameInfoText {
    private JTextField textField;

    /**
     * Creating JTextField and setting its parameters
     */
    public GameInfoText() {
        textField = new JTextField();
        final int frameWidth = Integer.valueOf(AppConfiguration.getInstance().getProperties().getProperty("panelWidth"));
        setTextFieldParameters(textField, frameWidth);
    }

    public JTextField getTextField(){
        return textField;
    }

    /**
     * Setting text field's parameters
     *
     * @param textField  text field above the board with rectangles
     * @param frameWidth width of the app's frame
     */
    private void setTextFieldParameters(JTextField textField, int frameWidth) {
        textField.setPreferredSize(new Dimension(frameWidth, TEXT_FIELD_HEIGHT));
        textField.setFont(textField.getFont().deriveFont(TEXT_FIELD_FONT_SIZE));
        ResourceBundle textFieldBundle = null;
        try {
            textFieldBundle = ResourceBundle.getBundle("Language/InGame/NorthernTextField");
        } catch (Exception e) {
            System.err.println("Can't load the frame's title from Language/InGame/NorthernTextField.properties!");
            e.printStackTrace();
            System.exit(1);
        }

        textField.setText(textFieldBundle.getString("lives_left") + "4" + "   " + // + 4 -> + getNumberOfLivesLeft()
                textFieldBundle.getString("time_left") + "120s");  // +120s -> +getNumberOfSecondsLeft()
        textField.setEditable(false);
    }

}
