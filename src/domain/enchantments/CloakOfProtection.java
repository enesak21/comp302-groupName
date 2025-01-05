package domain.enchantments;

import domain.game.Game;

/**
 * This enchantment provides invisibility for given duration time against monsters
 */
public class CloakOfProtection extends Enchantment{
    private int effectDuration;

    public CloakOfProtection(String type, float visibilityDuration) {
        super(type, visibilityDuration);
    }

    /**
     * gets the duration time of cloak of protection enchantment
     * @return
     */
    public int getEffectDuration() {
        return effectDuration;
    }

    /**
     * sets the duration time of cloak of protection enchantment
     * @param effectDuration
     */
    public void setEffectDuration(int effectDuration) {
        this.effectDuration = effectDuration;
    }

    @Override
    public void applyEffect(Game game) {

    }
}