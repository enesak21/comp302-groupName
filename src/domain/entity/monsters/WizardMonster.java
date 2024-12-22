package domain.entity.monsters;

import domain.entity.Entity;
import domain.entity.playerObjects.Player;

public class WizardMonster extends BaseMonster{
    private float teleportFrequency = 5; //It teleports the rune in every 5 sec.

    public WizardMonster(int gridX, int gridY, int tileSize) {
        super(gridX, gridY, tileSize);
    }

    @Override
    public void update() {

    }

    @Override
    public void update(Player player) {
        attack(player);
        move();
    }

    @Override
    public void attack(Player player) {
        //a.k.a switchRune()
    }

    @Override
    public void move(){

    }
}