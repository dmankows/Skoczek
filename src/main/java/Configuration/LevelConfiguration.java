package Configuration;

import MainMenuGUI.InfoBox;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.awt.*;
import java.io.File;
import java.util.ResourceBundle;


/**
 * Class responsible for reading configuration of every level of our game
 */
public class LevelConfiguration extends Configuration {
    /**
     * BoardElements (enum) table in which we have information where we should draw rectangles and character
     */
    private BoardElements[][] blocksArray;
    /**
     * Defines how many blocks fit vertically on the board
     */
    private int numberOfColumns;
    /**
     * Defines how many blocks fit horizontally on the board
     */
    private int numberOfRows;
    /**
     * Blocks' color
     */
    private Color blockColor;
    /**
     * Blocks' stroke color
     */
    private Color strokeColor;
    /**
     * Board's background color
     */
    private Color backgroundColor;
    /**
     * Character's color
     */
    private Color characterColor;
    /**
     * Character's start position on the X axis (the center of our character) - in pixels
     */
    private int characterXStartPosition;
    /**
     * Character's start position on the Y axis (the center of our character) - in pixels
     */
    private int characterYStartPosition;
    /**
     * Block's height - in pixels
     */
    private int blockHeight;
    /**
     * Block's width - in pixels
     */
    private int blockWidth;
    /**
     * Block's stroke width - in pixels
     */
    private int blockStrokeWidth;
    /**
     * Character's size - width in pixels
     */
    private int characterWidth;
    /**
     * Character's size - height in pixels
     */
    private int characterHeight;
    /**
     * JPanel's width - in pixels
     */
    private int gamePanelWidth;
    /**
     * JPanel's height - in pixels
     */
    private int gamePanelHeight;
    /**
     * Level config object
     */
    private PropertiesConfiguration levelConfig;
    /**
     * Singleton
     */
    private static Configurations configurations = new Configurations();

    /**
     * Setting basic stuff.
     */
    public LevelConfiguration() {
        String levelConfigPath = AppConfiguration.getInstance().getString("level_1_config_path");
        loadConfigurationProperties(levelConfigPath);

        numberOfRows = getInt("number_of_rows", levelConfigPath);
        numberOfColumns = getInt("number_of_columns", levelConfigPath);
        blockStrokeWidth = getInt("block_stroke_width", levelConfigPath);
        blockColor = getColor("block_color", levelConfigPath);
        strokeColor = getColor("stroke_color", levelConfigPath);
        backgroundColor = getColor("background_color", levelConfigPath);
        characterColor = getColor("character_color", levelConfigPath);


        blocksArray = new BoardElements[numberOfRows][numberOfColumns];
        fillBlocksArray(blocksArray, numberOfColumns, numberOfRows, levelConfigPath);

        gamePanelWidth = AppConfiguration.getInstance().getInt("game_panel_width");
        gamePanelHeight = AppConfiguration.getInstance().getInt("game_panel_height");
        blockWidth = gamePanelWidth / numberOfColumns;
        blockHeight = gamePanelHeight / numberOfRows;

        characterWidth = blockWidth / 2;
        characterHeight = blockHeight / 2;

        characterXStartPosition = characterXStartPosition * blockWidth + blockWidth / 4;
        characterYStartPosition = characterYStartPosition * blockHeight + blockHeight / 2 - blockStrokeWidth;
    }

    /**
     * This method gets data from {@code levelConfigPath}, checks for potential errors and fills {@code blocksArray}
     * with the information, where blocks and character are on the board
     *
     * @param blocksArray     BoardElements (enum) table in which we have information where we should draw
     *                        rectangles and character
     * @param numberOfColumns number of columns of a board - i.e. how many rectangles can be drawn horizontally
     * @param numberOfRows    number of rows of a board - i.e. how many rectangles can be drawn vertically
     * @param levelConfigPath path to level's configuration file - needed only for a proper message if an error
     *                        occurs
     */
    private void fillBlocksArray(BoardElements[][] blocksArray, int numberOfColumns, int numberOfRows,
                                 String levelConfigPath) {
        String key;
        String value;

        // o - lack of a block
        // x - block exists
        // * - our character

        int characterCounter = 0;   // will count how many times '*' showed up - the aftermath value should be 1

        for (int i = 0; i < numberOfRows; ++i) {
            key = "row" + (i + 1);

            value = getString(levelConfig, key, levelConfigPath);
            if (value.contains("*")) {
                ++characterCounter;
                if (characterCounter > 1) {             // more than 1 '*'
                    showErrorMessage(levelConfigPath, "many_character_instances");
                }
            }
            if (value.length() != numberOfColumns) {
                showErrorMessage(levelConfigPath, "number_of_columns");
            }
            if (i == 0 && !value.matches("[o]+")) {   // the first row must be empty, hence the only accepted
                // character is 'o'
                showErrorMessage(levelConfigPath, "first_row");
            }
            if (i == (numberOfRows - 1) && value.contains("*")) { // in the last row, we can't have '*'
                showErrorMessage(levelConfigPath, "last_row");
            }
            if (!value.matches("[ox*]+")) {          // in other rows only 'o', 'x' and '*' are acceptable
                showErrorMessage(levelConfigPath, "illegal_char");
            }

            for (int j = 0; j < numberOfColumns; ++j) {
                if (value.charAt(j) == 'o') {
                    blocksArray[i][j] = BoardElements.EMPTY;
                } else if (value.charAt(j) == 'x') {
                    blocksArray[i][j] = BoardElements.BLOCK;
                } else {  // value.charAt(j) == '*'
                    blocksArray[i][j] = BoardElements.CHARACTER;
                    characterXStartPosition = j;
                    characterYStartPosition = i;
                }
            }
        }

        if (characterCounter == 0) {                    // lack of '*'
            showErrorMessage(levelConfigPath, "missing_char");
        }
    }

    /**
     * Launches getColor function from Configuration class
     *
     * @param whatProperty Parameter that we want to get
     * @return Found parameter
     */
    private Color getColor(String whatProperty, String levelConfigFilePath) {
        return getColor(levelConfig, whatProperty, levelConfigFilePath);
    }

    /**
     * Launches getInt function from Configuration class
     *
     * @param whatProperty Parameter that we want to get
     * @return Found parameter
     */
    private int getInt(String whatProperty, String levelConfigFilePath) {
        return getInt(levelConfig, whatProperty, levelConfigFilePath);
    }

    /**
     * This function loads configuration from {@code levelConfigPath} file
     *
     * @param levelConfigPath Path to our configuration file
     */
    private void loadConfigurationProperties(String levelConfigPath) {
        try {
            levelConfig = configurations.properties(new File(levelConfigPath));
            levelConfig.setThrowExceptionOnMissing(true);
        } catch (ConfigurationException e) {
            e.printStackTrace();
            ResourceBundle bundle = Bundle.loadBundle("Language/InfoBox/Errors");
            InfoBox.infoBox(bundle.getString("couldnt_load_file") + levelConfigPath + "!\n" +
                    bundle.getString("closing_app"), bundle.getString("bar_title"));
            System.exit(1);
        }
    }

    /* GETTERS */

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getBlockColor() {

        return blockColor;
    }

    public double getBlockHeight() {
        return blockHeight;
    }

    public BoardElements[][] getBlocksArray() {
        return blocksArray;
    }

    public float getBlockStrokeWidth() {
        return blockStrokeWidth;
    }

    public int getBlockWidth() {
        return blockWidth;
    }

    public Color getCharacterColor() {
        return characterColor;
    }

    public int getCharacterHeight() {
        return characterHeight;
    }

    public int getCharacterWidth() {
        return characterWidth;
    }

    public int getCharacterXStartPosition() {
        return characterXStartPosition;
    }

    public int getCharacterYStartPosition() {
        return characterYStartPosition;
    }

    public int getGamePanelHeight() {
        return gamePanelHeight;
    }

    public int getGamePanelWidth() {
        return gamePanelWidth;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }
}
