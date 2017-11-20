package Configuration;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

/* in this file, if method is named load[...]., then it means that it loads data FROM a configuration file */

/**
 * Exception thrown in case some color couldn't be properly set
 */
class noColorException extends Exception {
    noColorException(String message) {
        super(message);
    }
}

/**
 * Class responsible for reading configuration of every level of our game
 */
public class LevelConfiguration {
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
    private float blockStrokeWidth;
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
     * Setting basic stuff.
     */
    public LevelConfiguration() {
        String levelConfigFilePath = AppConfiguration.getInstance().getProperties().getProperty("levelConfigFilePath");

        Properties levelProperties = new Properties();
        loadLevelProperties(levelProperties, levelConfigFilePath);

        numberOfRows = loadDimensionOfArray("numberOfRows", levelProperties, levelConfigFilePath);
        numberOfColumns = loadDimensionOfArray("numberOfColumns", levelProperties, levelConfigFilePath);
        blockStrokeWidth = loadStrokeWidth("blockStrokeWidth");

        blockColor = loadColor("blockColor", levelProperties, levelConfigFilePath);
        strokeColor = loadColor("strokeColor", levelProperties, levelConfigFilePath);
        backgroundColor = loadColor("backgroundColor", levelProperties, levelConfigFilePath);
        characterColor = loadColor("characterColor", levelProperties, levelConfigFilePath);

        blocksArray = new BoardElements[numberOfRows][numberOfColumns];
        fillBlocksArray(blocksArray, numberOfColumns, numberOfRows, levelProperties, levelConfigFilePath);

        gamePanelWidth = loadPanelDimension("panelWidth");
        gamePanelHeight = loadPanelDimension("panelHeight");
        blockWidth = gamePanelWidth / numberOfColumns;
        blockHeight = gamePanelHeight / numberOfRows;

        characterWidth = blockWidth / 2;
        characterHeight = blockHeight / 2;

        characterXStartPosition = characterXStartPosition * blockWidth + blockWidth / 4;
        characterYStartPosition = characterYStartPosition * blockHeight + blockHeight / 2 - (int) blockStrokeWidth;
    }

    /**
     * Loads value of either height or width of a game panel
     *
     * @param key defines whether we want height or width
     * @return found value, based upon {@code key} value
     */
    private int loadPanelDimension(String key) {
        int value;
        try {
            value = Integer.valueOf(AppConfiguration.getInstance().getProperties().getProperty(key));
        } catch (Exception e) {
            System.err.println("Can't get proper value of stroke's width! Setting default value...");
            e.printStackTrace();
            value = GameGUI.Constants.DEFAULT_PANEL_SIZE;
        }
        return value;
    }

    /**
     * Loads blockStrokeWidth from AppConfiguration file
     *
     * @param key defines the key under which we are looking for the value
     * @return the value of block's stroke width
     */
    private float loadStrokeWidth(String key) {
        float strokeWidth;
        try {
            strokeWidth = Float.parseFloat(AppConfiguration.getInstance().getProperties().getProperty(key));
        } catch (Exception e) {
            System.err.println("Can't get proper value of stroke's width! Setting default value...");
            e.printStackTrace();
            strokeWidth = GameGUI.Constants.DEFAULT_STROKE_WIDTH;
        }
        return strokeWidth;
    }

    /**
     * This method gets data from {@code levelProperties}, checks for potential errors and fills {@code blocksArray}
     * with the information, where blocks and character are on the board
     *
     * @param blocksArray         BoardElements (enum) table in which we have information where we should draw
     *                            rectangles and character
     * @param numberOfColumns     number of columns of a board - i.e. how many rectangles can be drawn horizontally
     * @param numberOfRows        number of rows of a board - i.e. how many rectangles can be drawn vertically
     * @param levelProperties     this object contains information about the properties of game's level
     * @param levelConfigFilePath path to level's configuration file - needed only for a proper message if an error
     *                            occurs
     */
    private void fillBlocksArray(BoardElements[][] blocksArray, int numberOfColumns, int numberOfRows,
                                 Properties levelProperties, String levelConfigFilePath) {
        String key;
        String value;

        // o - lack of block
        // x - block exists
        // * - our character

        int characterCounter = 0;   // will count how many times '*' showed up - the aftermath value should be 1

        for (int i = 0; i < numberOfRows; ++i) {
            key = "row" + (i + 1);

            value = levelProperties.getProperty(key);
            if (value.contains("*")) {
                ++characterCounter;
                if (characterCounter > 1) {             // more than 1 '*'
                    System.err.println("Incorrect " + levelConfigFilePath + " file - more than 1 instance of a " +
                            "character - '*'");
                    System.exit(1);
                }
            }
            if (value.length() != numberOfColumns) {
                System.err.println("Wrong number of columns in " + levelConfigFilePath + " file!");
                System.exit(1);
            }
            if (i == 0 && !value.matches("[o]+")) {   // the first row must be empty, hence the only accepted
                // character is 'o'
                System.err.println("Illegal characters in platform's first row in " +
                        levelConfigFilePath + " file!");
                System.exit(1);
            }
            if (i == (numberOfRows - 1) && value.contains("*")) { // in the last row, we can't have '*'
                System.err.println("Character cannot be positioned in the last row in " +
                        levelConfigFilePath + " file!");
                System.exit(1);
            }
            if (!value.matches("[ox*]+")) {          // in other rows only 'o', 'x' and '*' are acceptable
                System.err.println("Illegal characters in platform's setup in " +
                        levelConfigFilePath + " file - different from x and o.");
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
            System.err.println("Incorrect " + levelConfigFilePath + " file - missing '*' character!");
            System.exit(1);
        }
    }

    /**
     * This method gets the {@code whichColor} from {@code levelProperties} and checks if this is a valid color's name.
     * If it is - then the variable of type Color is returned.
     *
     * @param colorOfWhat         defines which color we want to get (block/block's stroke/board's background)
     * @param levelProperties     this object contains information about the properties of game's level
     * @param levelConfigFilePath path to level's configuration file - needed only for a proper message if an error
     *                            occurs
     * @return variable containing color's
     */
    private Color loadColor(String colorOfWhat, Properties levelProperties, String levelConfigFilePath) {
        Color color = null;
        String stringColor = levelProperties.getProperty(colorOfWhat);
        try {
            Field field = Class.forName("java.awt.Color").getField(stringColor);
            color = (Color) field.get(null);
            if (color == null) {
                throw new noColorException("Can't load " + colorOfWhat + " from " + levelConfigFilePath + " file!");
            }
        } catch (noColorException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Can't load color from " + levelConfigFilePath + " file!");
            e.printStackTrace();
            System.exit(1);
        }
        return color;
    }

    /**
     * This method gets {@code whatDimension} from {@code levelProperties}, checks for potential errors and returns
     * the value
     *
     * @param whatDimension       defines what dimension of the board we want to get (columns or rows)
     * @param levelProperties     this object contains information about the properties of game's level
     * @param levelConfigFilePath path to level's configuration file - needed only for a proper message if an error
     *                            occurs
     * @return Found value of {@code whatDimension}
     */
    private int loadDimensionOfArray(String whatDimension, Properties levelProperties, String levelConfigFilePath) {
        int arrayDimension = -1;          // initialized value - only because compiler requires so
        try {
            arrayDimension = Integer.parseInt(levelProperties.getProperty(whatDimension));
            if (arrayDimension < 1) {     // array's size must be >= 1
                throw new NegativeArraySizeException();
            }
        } catch (NegativeArraySizeException e) {
            System.err.println(levelConfigFilePath + ": Negative number of rows or columns!");
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Can't load proper data from" + levelConfigFilePath + " file!");
            e.printStackTrace();
            System.exit(1);
        }
        return arrayDimension;
    }

    /**
     * This method reads data from {@code levelConfigFilePath} file and loads it into {@code levelProperties} object
     *
     * @param levelProperties     this object contains information about the properties of game's level
     * @param levelConfigFilePath path to level's configuration file - needed only for a proper message if an error
     *                            occurs
     */
    private void loadLevelProperties(Properties levelProperties, String levelConfigFilePath) {
        FileReader fr = null;
        try {
            fr = new FileReader(levelConfigFilePath);
            levelProperties.load(fr);
        } catch (FileNotFoundException e) {
            System.err.println("Can't find " + levelConfigFilePath + " file!");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            System.err.println(levelConfigFilePath + ": An unknown error has occurred!");
            e.printStackTrace();
            System.exit(1);
        }
        try {
            fr.close();
        } catch (IOException e) {
            System.err.println(levelConfigFilePath + ": Can't close FileReader stream!");
            e.printStackTrace();
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
