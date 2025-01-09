package domain.enchantments;

import domain.game.Game;

public class SpeedUp extends BaseEnchantment{
    String name;
    private long activationTime;
    public SpeedUp(int gridX, int gridY, int tileSize) {
        super(gridX, gridY, tileSize);
        this.name = "Speed Up";
        System.out.println("speedUp created");
    }

    @Override
    public void applyEffect(Game game) {
        System.out.println("ZİBBİDİ GONZALES");
        game.getPlayer().setSpeed(8);
    }

    public void update(Game game) {
        if (isActive() && System.currentTimeMillis() - activationTime >= 20_000) {
            game.getPlayer().setInvisibleToArchers(false);
            setActive(false);
            System.out.println("Cloak of Protection deactivated");
        }
    }

    public String getName() {return name;}
}
