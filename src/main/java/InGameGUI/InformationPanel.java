package InGameGUI;

import Configuration.AppConfiguration;
import Configuration.Bundle;
import MainMenuGUI.InfoBox;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * This class represents JTextField with the information about lives, time left, etc.
 */
public class InformationPanel {
    /**
     * JTextField that will show the information about the lives left, time left, etc.
     */
    private JTextField textField;
    /**
     * JTextField's width - in pixels
     */
    private int panelWidth;
    /**
     * JTextField's height - in pixels
     */
    private int panelHeight;
    /**
     * JTextField's font's size
     */
    private int panelFontSize;
    /**
     * JTextField's font's color
     */
    private Color panelFontColor;
    /**
     * JTextField's background color
     */
    private Color panelBackgroundColor;

    /**
     * Creating JTextField and setting its parameters
     */
    public InformationPanel() {
        textField = new JTextField();
        // at start, JTextFieldWidth == PanelWidth
        panelWidth = AppConfiguration.getInstance().getInt("game_panel_width");
        panelHeight = AppConfiguration.getInstance().getInt("game_JTextField_height");
        panelFontSize = AppConfiguration.getInstance().getInt("game_JTextField_font_size");
        panelFontColor = AppConfiguration.getInstance().getColor("game_JTextField_font_color");
        panelBackgroundColor = AppConfiguration.getInstance().getColor("game_JTextField_background_color");

        setTextFieldColors(textField, panelFontColor, panelBackgroundColor);
        setTextFieldParameters(textField, panelWidth, panelHeight, panelFontSize);
        textField.setBorder(BorderFactory.createEmptyBorder());
    }


// TODO swingutilites invokelater
    /**
     * Setting text field's parameters
     *
     * @param textField  text field above the board with rectangles
     */
    private void setTextFieldParameters(JTextField textField, int panelWidth, int panelHeight, int panelFontSize) {
        String bundlePath = "Language/InGame/NorthernTextField";
        textField.setPreferredSize(new Dimension(panelWidth, panelHeight));
        textField.setFont(textField.getFont().deriveFont(panelFontSize));
        ResourceBundle textFieldBundle = null;
        try {
            textFieldBundle = ResourceBundle.getBundle(bundlePath);
        } catch (Exception e) {
            ResourceBundle bundle = Bundle.loadBundle("Language/InfoBox/Errors");
            InfoBox.infoBox(bundle.getString("couldnt_load_file") + bundlePath + "!\n" +
                    bundle.getString("closing_app"), bundle.getString("bar_title"));

            e.printStackTrace();
            System.exit(1);
        }

        textField.setText(" " + textFieldBundle.getString("lives_left") + "4" + "   " + // + 4 -> + getNumberOfLivesLeft()
                textFieldBundle.getString("time_left") + "120s");  // +120s -> +getNumberOfSecondsLeft()
        textField.setEditable(false);
    }

    private void setTextFieldColors(JTextField textField, Color panelFontColor, Color panelBackgroundColor){
        SwingUtilities.invokeLater(() -> {
            textField.setBackground(panelBackgroundColor);
            textField.setForeground(panelFontColor);
        });
    }

    public JTextField getTextField() {
        return textField;
    }


}
