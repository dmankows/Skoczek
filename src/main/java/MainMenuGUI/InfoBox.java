package MainMenuGUI;

import javax.swing.*;

/**
 * This class is responsible for showing message boxes that need to be closed by the user (e.g. can't load config file)
 */
public class InfoBox {
    /**
     * Shows message box. Usage: infoBox("YOUR INFORMATION HERE", "TITLE BAR MESSAGE");
     * @param infoMessage Information message like "Can't load config file"
     * @param titleBar The tile of the box's bar
     */
    public static void infoBox(String infoMessage, String titleBar){
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, infoMessage, titleBar,
                JOptionPane.INFORMATION_MESSAGE));
    }
}
