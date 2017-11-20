package GamePlay;

import Configuration.*;

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

    /**
     * Setting start position of the character
     *
     * @param levelConfiguration contains configuration of the level
     */
    public Character(LevelConfiguration levelConfiguration) {
        positionX = levelConfiguration.getCharacterXStartPosition();
        positionY = levelConfiguration.getCharacterYStartPosition();
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
