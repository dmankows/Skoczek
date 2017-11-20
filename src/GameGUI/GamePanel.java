package GameGUI;

import Configuration.*;
import GamePlay.GameCharacter;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

/**
 * Class which represents part of window where the game will take place
 */
public class GamePanel {

    private JPanel panel;

    public GamePanel(LevelConfiguration levelConfiguration) {
        int gamePanelWidth = levelConfiguration.getGamePanelWidth();
        int gamePanelHeight = levelConfiguration.getGamePanelHeight();

        panel = new JPanel(true) {
            @Override
            public void paint(Graphics g) {
                super.paintComponent(g);
                drawRectangles(g, gamePanelHeight, gamePanelWidth, levelConfiguration);
                drawCharacter(g, gamePanelHeight, gamePanelWidth, levelConfiguration);
            }
        };
        panel.setLayout(new FlowLayout());
        panel.setPreferredSize(new Dimension(gamePanelWidth, gamePanelHeight));
        panel.setBackground(levelConfiguration.getBackgroundColor());
    }

    /**
     * Calculate block's width and height, draw rectangles in the proper positions
     *
     * @param g               object that we use to draw rectangles
     * @param gamePanelHeight panel's height
     * @param gamePanelWidth  panel's width
     */
    private void drawRectangles(Graphics g, int gamePanelHeight, int gamePanelWidth, LevelConfiguration
            levelConfiguration) {
        int numberOfColumns = levelConfiguration.getNumberOfColumns();
        int numberOfRows = levelConfiguration.getNumberOfRows();
        BoardElements[][] blocksArray = levelConfiguration.getBlocksArray(); // TODO zmienic to na level - klasa z levelcfg

        final double BLOCK_HEIGHT = levelConfiguration.getBlockHeight();
        final double BLOCK_WIDTH = levelConfiguration.getBlockWidth();
        final float STROKE_WIDTH = levelConfiguration.getBlockStrokeWidth();

        Color blockColor = levelConfiguration.getBlockColor();
        Color strokeColor = levelConfiguration.getStrokeColor();

        Graphics2D g2d = (Graphics2D) g;
        // turn on anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Rectangle2D rectangle;

        // drawing rectangles, based upon the information from blocksArray
        for (int i = 0; i < numberOfRows; ++i) {
            for (int j = 0; j < numberOfColumns; ++j) {
                if (blocksArray[i][j] == BoardElements.BLOCK) {
                    rectangle = new Rectangle2D.Double(j * BLOCK_WIDTH, i * BLOCK_HEIGHT,
                            BLOCK_WIDTH, BLOCK_HEIGHT);
                    g2d.setColor(blockColor);
                    g2d.fill(rectangle);
                    g2d.setColor(strokeColor);
                    g2d.setStroke(new BasicStroke(STROKE_WIDTH));
                    g2d.draw(rectangle);
                }
            }
        }
    }

    private void drawCharacter(Graphics g, int gamePanelHeight, int gamePanelWidth, GameCharacter gameCharacter,
                               LevelConfiguration levelConfiguration){
        Graphics2D g2d = (Graphics2D) g;
        // turn on anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Rectangle2D rectangle;


    }

    public JPanel getPanel() {
        return panel;
    }
}
