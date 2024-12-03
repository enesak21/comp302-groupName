package domain.enchantments;

/**
 * Parent class for enchantments
 */
public class Enchantment {
    public String type;
    public float visibilityDuration;

    public Enchantment(String type, float visibilityDuration) {
        this.type = type;
        this.visibilityDuration = visibilityDuration;
    }
}