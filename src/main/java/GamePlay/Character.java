package GamePlay;

import Configuration.*;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * This class contains parameters of our character (position, color and sizes)
 */
public class Character {
    /**
     * Position of the center of our character on the X axis - in pixels
     */
    private int positionX;
    /**
     * Position of the center of our character on the Y axis - in pixels
     */
    private int positionY;
    private int characterWidth;
    private int characterHeight;
    private Color characterColor;

    /**
     * Setting start position of the character
     *
     * @param levelConfiguration contains default configuration of the level
     */
    public Character(LevelConfiguration levelConfiguration) {
        positionX = levelConfiguration.getCharacterXStartPosition();
        positionY = levelConfiguration.getCharacterYStartPosition();
        characterWidth = levelConfiguration.getCharacterWidth();
        characterHeight = levelConfiguration.getCharacterHeight();
        characterColor = levelConfiguration.getCharacterColor();
    }

    /**
     * Draws circle on our JPanel
     *
     * @param g  object that we use to draw our character
     */
    public void drawCharacter(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(characterColor);
        g2d.fillOval(positionX, positionY, characterWidth, characterHeight);

        // turn on anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    /* GETTERS */

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    /* SETTERS */

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
