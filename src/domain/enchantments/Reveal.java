package domain.enchantments;

public class Reveal extends Enchantment {

    private int effectDuration;
    public Reveal(String type, float visibilityDuration) {
        super(type, visibilityDuration);
    }

    public int getEffectDuration() {
        return effectDuration;
    }

    public void setEffectDuration(int effectDuration) {
        this.effectDuration = effectDuration;
    }
}