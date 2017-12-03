package Configuration;

import MainMenuGUI.InfoBox;

import java.util.ResourceBundle;

/**
 * This class is used to loading Resource Bundle files
 */
public abstract class Bundle {
    /**
     * This method checks whether the bundle under the {@code path} path exists
     * @param path Path under which we search for the language file
     * @return found {@code bundle}
     */
    public static ResourceBundle loadBundle(String path) {
        ResourceBundle bundle = null;
        try {
            bundle = ResourceBundle.getBundle(path);
        } catch (Exception e) {
            InfoBox.infoBox("Incorrect or missing language file: " + path + "\nClosing application...",
                    "Error");
            e.printStackTrace();
            System.exit(1);
        }
        return bundle;
    }
}
