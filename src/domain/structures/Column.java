package domain.structures;

import domain.game.Tile;

/**
 * Represents a column structure in the game.
 */
public class Column extends Structure {
    public Column(Tile position) {
        super("column", position);
    }
}
