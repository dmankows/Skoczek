/* https://commons.apache.org/proper/commons-configuration/index.html */

package Configuration;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import MainMenuGUI.InfoBox;

import java.awt.*;
import java.io.File;
import java.util.ResourceBundle;

/**
 * This class is used to get parameters from the appConfigPath
 */
public class AppConfiguration extends Configuration {
    /**
     * Our only instance of this class - Singleton
     */
    private static AppConfiguration appConfigInstance = new AppConfiguration();
    /**
     * Needed for loading configuration parameters
     */
    private PropertiesConfiguration appConfig;
    /**
     * Contains path to the configuration file
     */
    private String appConfigPath;

    private AppConfiguration() {
        appConfigPath = "ApplicationConfig.properties";
        try {
            Configurations configurations = new Configurations();
            appConfig = configurations.properties(new File(appConfigPath));
            appConfig.setThrowExceptionOnMissing(true);

        } catch (ConfigurationException e) {
            e.printStackTrace();
            ResourceBundle bundle = Bundle.loadBundle("Language/InfoBox/Errors");
            InfoBox.infoBox(bundle.getString("couldnt_load_file") + appConfigPath + "!\n" +
                    bundle.getString("closing_app"), bundle.getString("bar_title"));
            System.exit(1);
        }
    }

    /**
     * Launches getString function from Configuration class
     *
     * @param whatProperty Parameter that we want to get
     * @return Found parameter
     */
    public String getString(String whatProperty) {
        return getString(getAppConfig(), whatProperty, appConfigPath);
    }

    /**
     * Launches getInt function from Configuration class
     *
     * @param whatProperty Parameter that we want to get
     * @return Found parameter
     */
    public int getInt(String whatProperty) {
        return getInt(getAppConfig(), whatProperty, appConfigPath);
    }

    /**
     * Launches getColor function from Configuration class
     * @param whatColor What color we want
     * @return Found parameter
     */
    public Color getColor(String whatColor){
        return getColor(getAppConfig(), whatColor, appConfigPath);
    }

    /* GETTERS */
    public String getAppConfigPath() {
        return appConfigPath;
    }
    private PropertiesConfiguration getAppConfig(){
        return appConfig;
    }
    public static AppConfiguration getInstance(){
        return appConfigInstance;
    }
}


