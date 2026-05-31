package rpg.core;

/**
 * @author Amna Mirza
 */
public class StatusEffect {

    private String effectName;
    private int duration;
    private int intensity;

    public StatusEffect(String effectName, int duration, int intensity) {
        this.effectName = effectName;
        this.duration   = duration;
        this.intensity  = intensity;
    }

    /** Convenience constructor — default intensity 5 (used for Burning). */
    public StatusEffect(String effectName, int duration) {
        this(effectName, duration, 5);
    }

    public void reduceDuration() {
        if (duration > 0) duration--;
    }

    public boolean isType(String type)  { return this.effectName.equalsIgnoreCase(type); }
    public boolean isExpired()          { return duration <= 0; }

    public String getEffectName()       { return effectName; }
    public int getDuration()            { return duration; }
    public int getIntensity()           { return intensity; }
}
