import GameGUI.GameFrame;
import GameGUI.GameInfoText;
import GameGUI.GamePanel;
import Configuration.AppConfiguration;
import Configuration.LevelConfiguration;
import GamePlay.GameCharacter;

import java.awt.*;
import java.util.Locale;

/**
 * Main class - set language, load configuration, start app's window
 */
public class Main {

    public static void main(String[] args) {
        setLanguage();
        LevelConfiguration levelConfiguration = new LevelConfiguration();    // load level configuration

        GameCharacter gameCharacter = new GameCharacter(levelConfiguration);
        GameFrame gameFrame = new GameFrame();     // create application's window
        GamePanel gamePanel = new GamePanel(levelConfiguration);
        GameInfoText gameInfoText = new GameInfoText();
        gameFrame.addComponent(gamePanel.getPanel(), BorderLayout.SOUTH);
        gameFrame.addComponent(gameInfoText.getTextField(), BorderLayout.NORTH);
        gameFrame.showWindow();
    }

    /**
     * Chooses the default Locale for the whole application - based upon the
     * value loaded from ApplicationConfig.xml file.
     */
    private static void setLanguage() {
        String chosenLanguage = AppConfiguration.getInstance().getProperties().getProperty("appLanguage");

        switch (chosenLanguage) {
            case "english":
                Locale.setDefault(new Locale("en", "EN"));
                break;
            case "polish":
                Locale.setDefault(new Locale("pl", "PL"));
                break;
            default:
                System.err.println("Wrong language in ApplicationConfig.xml file!");
                System.exit(1);
        }
    }
}
