package GameGUI;

/**
 * Class in which we define every constant related to the game's GUI.
 */
final public class Constants {
    /**
     * JTextField's (within a JFrame) height - in pixels
     */
    static final int TEXT_FIELD_HEIGHT = 20;
    /**
     * Font size of text in JTextField (within a JFrame) - in pixels
     */
    static final float TEXT_FIELD_FONT_SIZE = 13f;
    /**
     * Default value of block's stroke width - in pixels
     */
    static final public float DEFAULT_STROKE_WIDTH = 1f;
    /**
     * Default size of game's panel (assume it is a square) - in pixels
     */
    static final public int DEFAULT_PANEL_SIZE = 900;

    /**
     * This class cannot have "representation", nor can it be abstract - hence in case of attempt of creating an
     * object, throw the AssertionError exception.
     */
    private Constants() {
        throw new AssertionError();
    }
}
