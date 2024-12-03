package domain.enchantments;

public class ExtraTime extends Enchantment {
    private float duration;

    public ExtraTime(String type, float visibilityDuration) {
        super(type, visibilityDuration);
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }
}