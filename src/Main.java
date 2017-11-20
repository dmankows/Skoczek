import GamePlay.Board;
import GamePlay.Character;
import InGameGUI.Frame;
import InGameGUI.InfoText;
import InGameGUI.Panel;
import Configuration.AppConfiguration;
import Configuration.LevelConfiguration;

import java.awt.*;
import java.util.Locale;

/**
 * Main class - set language, load configuration, show app's window
 */
public class Main {

    public static void main(String[] args) {
        setLanguage();
        LevelConfiguration levelConfiguration = new LevelConfiguration();    // load level configuration

        Character character = new Character(levelConfiguration);
        Board board = new Board(levelConfiguration);
        Frame frame = new Frame();                                          // create application's window
        Panel panel = new Panel(levelConfiguration, character, board);      // create panel and draw board and character
        frame.addComponent(panel.getPanel(), BorderLayout.SOUTH);

        InfoText infoText = new InfoText();                                 // create text field above the panel
        frame.addComponent(infoText.getTextField(), BorderLayout.NORTH);
        frame.showWindow();
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
