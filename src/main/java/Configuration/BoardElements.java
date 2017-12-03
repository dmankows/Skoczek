package Configuration;

/**
 * Enum that defines whether the element in the array is empty, it is block, highlighted block or a character.
 * We need those information to
 * 1) draw a board and a character at the beginning of the level
 * 2) draw a board depending where we moved the character (so the blocks can disappear or be highlighted)
 */
public enum BoardElements {
    EMPTY, BLOCK, HIGHLIGHTED, CHARACTER
}
