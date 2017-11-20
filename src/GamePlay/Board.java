package GamePlay;

import Configuration.BoardElements;
import Configuration.LevelConfiguration;

public class Board {
    /**
     * BoardElements (enum) table in which we have information where we should draw rectangles and character
     */
    private BoardElements[][] blocksArray;

    public Board(LevelConfiguration levelConfiguration){
        blocksArray = levelConfiguration.getBlocksArray();
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
