package domain.enchantments;

/**
 * This enchantment provides invisibility for given duration time against monsters
 */
public class CloakOfProtection{
    private int effectDuration;

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
}