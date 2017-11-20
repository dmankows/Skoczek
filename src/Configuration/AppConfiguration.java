package Configuration;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;

/**
 * Class responsible for loading data from app's configuration file and loading it into properties object.
 * We have only one instance of this class in the whole project - thus, Singleton is being used here.
 */
public class AppConfiguration {

    /**
     * Singleton - the only instance of this class in the whole project
     */
    private static AppConfiguration appConfigInstance = new AppConfiguration();
    /**
     * Contains application's configuration
     */
    private Properties properties;

    private AppConfiguration() {
        String appConfigurationPath = "res/ApplicationConfig.xml";
        File file = getFile(appConfigurationPath);
        Document doc = parseDataFromFile(file, appConfigurationPath);
        properties = new Properties();
        loadProperty("panelWidth", doc, properties);
        loadProperty("panelHeight", doc, properties);
        loadProperty("appLanguage", doc, properties);
        loadProperty("levelConfigFilePath", doc, properties);
        loadProperty("blockStrokeWidth", doc, properties);
    }

    /**
     * Loads the file from {@code appConfigurationPath}, checks for potential errors and returns it
     *
     * @param appConfigurationPath path to the application's configuration file
     * @return File "paired" with {@code appConfigurationPath} - .xml file
     */
    private File getFile(String appConfigurationPath) {
        File file = null;
        try {
            file = new File(appConfigurationPath);
            if (!file.exists()) {
                throw new FileNotFoundException("Could not find file: " + appConfigurationPath);
            }
        } catch (NullPointerException e) {
            System.err.println("Error - incorrect data inside the file " + appConfigurationPath);
            e.printStackTrace();
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println("Error - file " + appConfigurationPath + " doesn't exist!");
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            System.err.println(appConfigurationPath + ": An unknown error has occurred!");
            e.printStackTrace();
            System.exit(1);
        }
        return file;
    }

    public static AppConfiguration getInstance() {
        return appConfigInstance;
    }

    public Properties getProperties() {
        return properties;
    }

    /**
     * Loads property {@code whatProperty} from {@code doc} into {@code properties} object
     *
     * @param whatProperty string that defines what property we want to get from Document doc
     * @param doc          this variable keeps the data loaded from res/ApplicationConfig.xml file
     * @param properties   {@code whatProperty} from {@code doc} is loaded to this variable
     */
    private void loadProperty(String whatProperty, Document doc, Properties properties) {

        /*
         * We have a doc object, that contains data from the XML file and the method getElementsByTagName searches
         * for the first (item(0)) occurrence of "whatProperty" and then returns the value paired with this
         * tag (getTextContent)
         */

        String temp = doc.getElementsByTagName(whatProperty).item(0).getTextContent();
        properties.setProperty(whatProperty, temp);
    }

    /**
     * This method parses data from {@code file} and loads into the variable of type Document - and then returns it
     *
     * @param file       from this file the data is read
     * @param configPath contains path to the configuration file - needed only for a proper message in case of
     *                   occurrence of an error
     * @return Variable of type Document with loaded and parsed data from {@code file}
     */
    private Document parseDataFromFile(File file, String configPath) {
        Document doc = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            System.err.println("Cannot parse data from " + configPath);
            e.printStackTrace();
            System.exit(1);
        }
        return doc;
    }
}
