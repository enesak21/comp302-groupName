package domain.enchantments;

import domain.entity.playerObjects.Player;
import domain.game.Game;

public class ExtraLife extends Enchantment{

    public ExtraLife(String type, float visibilityDuration) {
        super(type, visibilityDuration);
    }
    public void addExtraTime(Game game) {
        game.setRemainingTime(game.getRemainingTime()+5);
    }
}