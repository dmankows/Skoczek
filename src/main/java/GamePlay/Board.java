package GamePlay;

import Configuration.BoardElements;
import Configuration.LevelConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * This class represents the board of our game (positions of rectangles)
 */
public class Board {
    /**
     * BoardElements (enum) table in which we have information where we should draw rectangles and character
     */
    private BoardElements[][] blocksArray;
    private int numberOfColumns;
    private int numberOfRows;
    private double BLOCK_HEIGHT;
    private double BLOCK_WIDTH;
    private float STROKE_WIDTH;
    private Color blockColor;
    private Color strokeColor;


    public Board(LevelConfiguration levelConfiguration) {
        blocksArray = levelConfiguration.getBlocksArray();
        numberOfColumns = levelConfiguration.getNumberOfColumns();
        numberOfRows = levelConfiguration.getNumberOfRows();
        BLOCK_HEIGHT = levelConfiguration.getBlockHeight();
        BLOCK_WIDTH = levelConfiguration.getBlockWidth();
        STROKE_WIDTH = levelConfiguration.getBlockStrokeWidth();
        blockColor = levelConfiguration.getBlockColor();
        strokeColor = levelConfiguration.getStrokeColor();

    }

    /**
     * Calculate block's width and height, draw rectangles in the proper positions
     *
     * @param g                  object that we use to draw rectangles
     */
    public void drawBoard(Graphics g) {
//        Stroke stroke = new BasicStroke(STROKE_WIDTH);
//        Graphics2D g2d = (Graphics2D) g;
//        Rectangle2D rectangle;

        // drawing rectangles, based upon the information from blocksArray
        for (int i = 0; i < numberOfRows; ++i) {
            for (int j = 0; j < numberOfColumns; ++j) {
                if (blocksArray[i][j] == BoardElements.BLOCK) {
                    g.drawRect((int) (j * BLOCK_WIDTH), (int) (i * BLOCK_HEIGHT),
                            (int) BLOCK_WIDTH, (int) BLOCK_HEIGHT);
                    g.setColor(blockColor);
                    g.fillRect((int) (j * BLOCK_WIDTH), (int) (i * BLOCK_HEIGHT),
                            (int) BLOCK_WIDTH, (int) BLOCK_HEIGHT);
                    g.setColor(strokeColor);
                    g.drawRect((int) (j * BLOCK_WIDTH), (int) (i * BLOCK_HEIGHT),
                            (int) BLOCK_WIDTH + (int) STROKE_WIDTH, (int) BLOCK_HEIGHT + (int) STROKE_WIDTH);

                }
            }
        }
    }

    /* GETTERS */

    public BoardElements[][] getBlocksArray() {
        return blocksArray;
    }

    /* SETTERS */

    public void setBlocksArray(BoardElements[][] blocksArray) {
        this.blocksArray = blocksArray;
    }
}
