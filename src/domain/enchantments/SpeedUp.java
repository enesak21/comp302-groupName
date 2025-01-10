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
        if(!isActive()){
            activationTime = System.currentTimeMillis();
            game.getActiveEnchantments().add(this);
            setActive(true);
            //System.out.println("ZİBBİDİ GONZALES");
            game.getPlayer().setSpeed(8);
        }

    }

    public void update(Game game) {

        if (isActive() && System.currentTimeMillis() - activationTime >= 5_000) {
            //game.getPlayer().setInvisibleToArchers(false);
            slowDown(game);
            setActive(false);

           // System.out.println("Speed Up Deactivated");


        }
    }

    public void slowDown(Game game) {
        game.getPlayer().setSpeed(4);
        setActive(false);
    }

    public String getName() {return name;}
}
