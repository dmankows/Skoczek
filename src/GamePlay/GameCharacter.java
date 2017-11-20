package GamePlay;

import Configuration.*;

import java.awt.*;

/**
 * This class contains parameters of our character (position, color and sizes)
 */
public class GameCharacter {
    private int positionX;          // X-position of the center of our character - in pixels
    private int positionY;          // Y-position of the center of our character - in pixels
    private Color characterColor;

    /**
     * Setting start position of the character
     * @param levelConfiguration
     */
    public GameCharacter(LevelConfiguration levelConfiguration) {
        positionX = levelConfiguration.getCharacterXStartPosition();
        positionY = levelConfiguration.getCharacterYStartPosition();
        characterColor = levelConfiguration.getCharacterColor();
    }

    /* GETTERS */

    public Color getCharacterColor() {
        return characterColor;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }


    /* SETTERS */

    public void setCharacterColor(Color characterColor) {
        this.characterColor = characterColor;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
