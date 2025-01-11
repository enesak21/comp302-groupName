package domain.game;

import domain.structures.Structure;

public class Hall {
    private final String name;
    private final int minStructures;
    private final String[][] grid;
    private int placedStructuresCount;
    private final int maxStructures = 32;

    public Hall(String name, int minStructures) {
        this.name = name;
        this.minStructures = minStructures;
        this.grid = new String[16][16];
        this.placedStructuresCount = 0;
    }

    public void placeStructure(int x, int y, String structureKey) {
        if (grid[x][y] == null) {
            grid[x][y] = structureKey;
            placedStructuresCount++;
        }
    }

    public void placeRandomStructures(int numStructuresToPlace) {
        int numStructuresCanBePlaced = maxStructures - placedStructuresCount;
        numStructuresToPlace = Math.min(numStructuresToPlace, numStructuresCanBePlaced);
        while (numStructuresToPlace > 0) {
            int x = (int) (Math.random() * grid.length);
            int y = (int) (Math.random() * grid[0].length);
            if (grid[x][y] == null) {
                grid[x][y] = StructureObserver.getRandomStructure();
                placedStructuresCount++;
                numStructuresToPlace--;
            }
        }
    }

    public void placeRandomStructures() {
        placeRandomStructures(minStructures);
    }

    public void removeStructure(int x, int y) {
        if (grid[x][y] != null) {
            grid[x][y] = null;
            placedStructuresCount--;
        }
    }

    public boolean isRequirementMet() {
        return placedStructuresCount >= minStructures;
    }

    public int getPlacedStructuresCount() {
        return placedStructuresCount;
    }

    public int getMinStructures() {
        return minStructures;
    }

    public String[][] getGrid() {
        return grid;
    }

    public void setGrid(String[][] newGrid) {
        for (int i = 0; i < grid.length; i++) {
            System.arraycopy(newGrid[i], 0, grid[i], 0, grid[i].length);
        }
    }

    public String getName() {
        return name;
    }

    /**
     * Converts the Hall's grid to a corresponding Grid object.
     *
     * @param tileSize The size of each tile.
     * @return A Grid object representing the Hall's grid.
     */
    public Grid toGrid(int tileSize) {
        Grid gridObject = new Grid(tileSize);
        Tile[][] tiles = gridObject.getTiles();

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                String structureKey = grid[x][y];
                if (structureKey != null) {
                    // Create a Structure object for the tile if there's a structure key
                    Structure structure = new Structure(structureKey, tiles[x][y]);
                    tiles[x][y].addStructure(structure);
                }
            }
        }

        return gridObject;
    }


}
