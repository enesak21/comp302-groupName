package domain.game1.enchantments;

import domain.game1.game.CollisionChecker;
import domain.game1.game.Game;
import domain.game1.game.Tile;

import java.awt.*;

public class Reveal extends BaseEnchantment {
    private Image icon;
    private long activationTime; // Timestamp when the effect was activated
    private final String name = "Reveal";

    public Reveal(int gridX, int gridY, int tileSize) {
        super(gridX, gridY, tileSize);
    }

    public String getName() {
        return name;
    }

    public Image getIcon() {
        return icon;
    }

    @Override
    public void applyEffect(Game game) {

        if (!isActive()) {
            setActive(true);
            game.getActiveEnchantments().add(this);
            activationTime = System.currentTimeMillis();
            Tile runeTile = game.getSearchRuneController().getRuneTile();

            if (runeTile != null) {
                highlightRegion(game, runeTile);

            }
        }
    }

    private void highlightRegion(Game game, Tile runeTile) {
        int runeX = runeTile.getGridX();
        int runeY = runeTile.getGridY();

        int startX = Math.max(runeX - 2, 0);
        int startY = Math.max(runeY - 2, 0);
        int endX = Math.min(runeX + 1, game.getGrid().getColumns() - 1);
        int endY = Math.min(runeY + 1, game.getGrid().getRows() - 1);

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                game.getGrid().getTileAt(x, y).setHighlighted(true);
            }
        }
    }

    public void update(Game game) {
        if (isActive() && System.currentTimeMillis() - activationTime >= 10_000) {
            deactivateRegion(game);
            setActive(false);
        }
    }

    private void deactivateRegion(Game game) {
        Tile runeTile = game.getSearchRuneController().getRuneTile();
        if (runeTile != null) {
            int runeX = runeTile.getGridX();
            int runeY = runeTile.getGridY();

            int startX = Math.max(runeX - 2, 0);
            int startY = Math.max(runeY - 2, 0);
            int endX = Math.min(runeX + 1, game.getGrid().getColumns() - 1);
            int endY = Math.min(runeY + 1, game.getGrid().getRows() - 1);

            for (int x = startX; x <= endX; x++) {
                for (int y = startY; y <= endY; y++) {
                    game.getGrid().getTileAt(x, y).setHighlighted(false);
                }
            }
        }
    }

    @Override
    public void setCollisionChecker(CollisionChecker collisionChecker) {
        // Not needed for this enchantment
    }

}
