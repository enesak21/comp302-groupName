package domain.entity.monsters;

import domain.entity.Entity;

public class WizardMonster extends BaseMonster{
    private float teleportFrequency = 5; //It teleports the rune in every 5 sec.

    public WizardMonster(int gridX, int gridY, int tileSize) {
        super(gridX, gridY, tileSize);
    }


    private void switchRune() {}


    @Override
    public void update() {

    }

    @Override
    public void attack() {
        //Wizard does not attack
    }

}