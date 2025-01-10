package test;

import domain.entity.Direction;
import domain.entity.Entity;
import domain.game.CollisionChecker;
import domain.game.Grid;
import domain.game.Tile;
import domain.structures.Structure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CollisionCheckerTest {

    private CollisionChecker collisionChecker;
    private Grid grid;
    private Entity entity;

    @Before
    public void setup() {
        // 5x5 grid
        grid = new Grid(16);
        grid.setColumns(5);
        grid.setRows(5);

        // Grid içindeki her bir tile'ı oluştur
        Tile[][] tiles = new Tile[5][5];
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                tiles[x][y] = new Tile(x, y, false, null);
            }
        }
        grid.setTiles(tiles);

        // CollisionChecker ve Entity oluştur
        collisionChecker = new CollisionChecker(grid);
        entity = new Entity(2, 2, 16) {
            @Override
            public void update() {
                // Test için boş bir update metodu
            }
        };
    }

    @Test
    public void testCollisionWithSolidTile() {
        // Arrange: Solid bir tile oluştur ve entity'nin yönünü ayarla
        Tile solidTile = grid.getTileAt(2, 3);
        entity.setGridX(2);
        entity.setGridY(2);
        solidTile.setSolid(true);
        entity.setDirection(Direction.DOWN);

        // Act: Çarpışma kontrolü yap
        boolean result = collisionChecker.checkCollision(entity);

        // Assert: Çarpışma bekleniyor
        assertTrue("Solid bir tile'a hareket ederken çarpışma bekleniyor.", result);
    }

    @Test
    public void testNoCollisionWithNonSolidTile() {
        // Arrange: Non-solid bir tile oluştur ve entity'nin yönünü ayarla
        Tile nonSolidTile = grid.getTileAt(3, 2);
        Structure structure = new Structure("chest", nonSolidTile);
        nonSolidTile.addStructure(structure);
        entity.setDirection(Direction.RIGHT);

        // Act: Çarpışma kontrolü yap
        boolean result = collisionChecker.checkCollision(entity);

        // Assert: Çarpışma beklenmiyor
        assertFalse("Non-solid bir tile'a hareket ederken çarpışma beklenmiyor.", result);
    }

    @Test
    public void testCollisionOutOfBounds() {
        // Arrange: Entity'nin yönünü grid sınırlarının dışına ayarla
        entity.setDirection(Direction.UP);
        entity.setGridY(0); // Entity grid'in üst sınırında

        // Act: Çarpışma kontrolü yap
        boolean result = collisionChecker.checkCollision(entity);

        // Assert: Çarpışma bekleniyor (sınır dışı hareket)
        assertTrue("Sınır dışına hareket ederken çarpışma bekleniyor.", result);
    }
}
