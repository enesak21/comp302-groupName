package domain.enchantments;
import domain.game.CollisionChecker;
import domain.game.Game;
import domain.game.Tile;

import java.awt.*;

/**
 * This enchantment provides invisibility for given duration time against monsters
 */
public class CloakOfProtection extends BaseEnchantment {
    private Image icon;
    private long activationTime; // Timestamp when the effect was activated
    private final String name = "Cloak of Protection";

    public CloakOfProtection(int gridX, int gridY, int tileSize) {
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
            game.getPlayer().setInvisibleToArchers(true);
        }
    }



    public void update(Game game) {
        if (isActive() && System.currentTimeMillis() - activationTime >= 10_000) {
            game.getPlayer().setInvisibleToArchers(false);
            setActive(false);
            System.out.println("Cloak of Protection deactivated");
        }
    }

    @Override
    public void setCollisionChecker(CollisionChecker collisionChecker) {
        // Not needed for this enchantment
    }


}