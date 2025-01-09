package domain.enchantments;

import domain.enchantments.BaseEnchantment;
import domain.game.CollisionChecker;
import domain.game.Game;
import domain.game.Tile;
import domain.panels.PlayModePanel;

import java.awt.*;

public class Reveal extends BaseEnchantment {
    private Image icon;
    private boolean isActive;
    private long activationTime; // Timestamp when the effect was activated
    private final String name = "Reveal";

    public Reveal(int gridX, int gridY, int tileSize) {
        super(gridX, gridY, tileSize);
        this.isActive = false;
    }

    public String getName() {
        return name;
    }

    public Image getIcon() {
        return icon;
    }

    @Override
    public void applyEffect(Game game) {
        //System.out.println("AREA WILL BE HIGHLITED");
        if (!isActive) {
            isActive = true;
            game.getActiveEnchantments().add(this);
            activationTime = System.currentTimeMillis();

            Tile runeTile = game.getSearchRuneController().getRuneTile();

            if (runeTile != null) {
                highlightRegion(game, runeTile);

            } else {
                System.out.println("Rune tile not found.");
            }
        }
    }

    private void highlightRegion(Game game, Tile runeTile) {
        //System.out.println("AREA HIGHLIGTEDDDDDDDDDDDD");
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

        //System.out.println("Region highlighted from (" + startX + ", " + startY + ") to (" + endX + ", " + endY + ").");
    }

    public void update(Game game) {
        if (isActive && System.currentTimeMillis() - activationTime >= 10_000) {
            deactivateRegion(game);
            System.out.println("Region Higlight removed. Deactivated Reveal.");
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

        isActive = false;
    }

    @Override
    public void setCollisionChecker(CollisionChecker collisionChecker) {
        // Not needed for this enchantment
    }

}
