package Configuration;

import MainMenuGUI.InfoBox;
import org.apache.commons.configuration2.PropertiesConfiguration;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

/**
 * Exception thrown in case some color couldn't be properly set
 */
class noColorException extends Exception {
    noColorException(String message) {
        super(message);
    }
}

/**
 * This is an abstract class that contains methods used by both LevelConfiguration and AppConfiguration classes.
 * Those are used to read properties from .properties files.
 */
public abstract class Configuration {

    /**
     * Reads string from {@code configFile}, returns it
     *
     * @param whatProperty Defines what property we want to get from the {@code configFile}
     * @param configFile   Path to the .properties file
     * @return Read string from configFile
     */
    String getString(PropertiesConfiguration configuration, String whatProperty, String configFile) {
        String property = "";       // initialized - only becau se compiler requires so
        try {
            property = configuration.getString(whatProperty);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            showErrorMessage("lacking_property", whatProperty, configFile);
            System.exit(1);
        }
        return property;            // won't be null - in case of an empty string, the NoSuchElementException is thrown
    }

    /**
     * Reads integer from {@code configFile}, returns it
     *
     * @param whatProperty Defines what property we want to get from the {@code configFile}
     * @param configFile   Path to the .properties file
     * @return Read integer from configFile
     */
    int getInt(PropertiesConfiguration configuration, String whatProperty, String configFile) {
        int property = -1;          // initialized - only because compiler requires so
        try {
            property = configuration.getInt(whatProperty);
        } catch (NoSuchElementException e) {
            showErrorMessage("lacking_property", whatProperty, configFile);
            e.printStackTrace();
            System.exit(1);
        }
        if (property <= 0) {      // we will use only positive numbers in config files
            showErrorMessage("wrong_property", whatProperty, configFile);
            System.exit(1);
        }
        return property;
    }

    /**
     * Reads string from {@code configFile}, converts it to Color type and returns it
     *
     * @param whatProperty Defines what property we want to get from the {@code configFile}
     * @param configFile   Path to the .properties file
     * @return Read string converted to Color type
     */
    Color getColor(PropertiesConfiguration configuration, String whatProperty, String configFile) {
        Color color = null;
        String stringColor = getString(configuration, whatProperty, configFile);
        try {
            Field field = Class.forName("java.awt.Color").getField(stringColor);
            color = (Color) field.get(null);
            if (color == null) {
                throw new noColorException("Can't load " + whatProperty + " from " + configFile + " file!");
            }
        } catch (noColorException e) {
            e.printStackTrace();
            showErrorMessage("wrong_color", whatProperty, configFile);
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("wrong_property", whatProperty, configFile);
            System.exit(1);
        }
        return color;
    }

    /**
     * Show info Box with the message about what error has occurred
     *
     * @param whatProperty What property wasn't correct
     * @param configFile   Path to the file containing {@code whatProperty}
     * @param whatIsWrong  What is wrong with the property
     */
    private void showErrorMessage(String whatIsWrong, String whatProperty, String configFile) {

        ResourceBundle bundle = Bundle.loadBundle("Language/InfoBox/Errors");

        InfoBox.infoBox(bundle.getString("incorrect_file") + configFile + "!\n" +
                        bundle.getString(whatIsWrong) + whatProperty + "\n" + bundle.getString("closing_app"),
                bundle.getString("bar_title"));

    }

    /**
     * Show info Box with the message about what error has occurred
     *
     * @param configFile  Path to the file that contains properties
     * @param whatIsWrong What is wrong with the property
     */
    void showErrorMessage(String configFile, String whatIsWrong) {
        ResourceBundle bundle = Bundle.loadBundle("Language/InfoBox/Errors");
        InfoBox.infoBox(bundle.getString("incorrect_file") + configFile + "\n" +
                bundle.getString(whatIsWrong) + "!\n" +
                bundle.getString("closing_app"), bundle.getString("bar_title"));
        System.exit(1);
    }
}
