import Configuration.AppConfiguration;
import Configuration.Bundle;
import MainMenuGUI.InfoBox;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Main class - set the language, load configuration, show app's window
 */
public class Main {

    public static void main(String[] args) {

        /*
         *   1. Process Phase: Update any 'state' variables based on user input and/or game events
         *   2. Render Phase: Perform rendering of UI based on 'state' variables
         *   3. Synch Phase: Sleep on loop until a certain time to ensure a consistent frame rate
         */

        setLanguage();
        new Jumper();
    }

    /**
     * Chooses the default Locale for the whole application - based upon the
     * value loaded from ApplicationConfig.properties file.
     */
    private static void setLanguage() {
        String language = AppConfiguration.getInstance().getString("language");
        switch (language) {
            case "english":
                Locale.setDefault(new Locale("en", "EN"));
                break;
            case "polish":
                Locale.setDefault(new Locale("pl", "PL"));
                break;
            default:
                ResourceBundle bundle = Bundle.loadBundle("Language/InfoBox/Errors");
                InfoBox.infoBox( bundle.getString("wrong_language") +
                        AppConfiguration.getInstance().getAppConfigPath() + "!\n" + bundle.getString("closing_app"),
                        bundle.getString("bar_title"));
                System.exit(1);
        }
    }
}
