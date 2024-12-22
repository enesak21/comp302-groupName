package domain.game;

public class Hall {
    private final String name;
    private final int minStructures;
    private final String[][] grid;
    private int placedStructuresCount;

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
}

