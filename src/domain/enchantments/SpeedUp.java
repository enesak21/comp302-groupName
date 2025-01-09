package domain.enchantments;

import domain.game.Game;

public class SpeedUp extends BaseEnchantment{
    String name;
    public SpeedUp(int gridX, int gridY, int tileSize) {
        super(gridX, gridY, tileSize);
        this.name = "Speed Up";
        System.out.println("speedUp created");
    }

    @Override
    public void applyEffect(Game game) {
        System.out.println("ZİBBİDİ GONZALES");
    }
    public String getName() {return name;}
}
