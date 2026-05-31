package rpg.core;

import java.util.List;
import java.util.ArrayList;
import rpg.interfaces.Defendable;

/**
 * @author Amna Mirza
 */
public abstract class GameCharacter implements Defendable {

    private String name;
    private int health;
    private int maxHealth;
    private int attack;
    private int defense;
    private List<StatusEffect> statusEffects = new ArrayList<>();

    public void applyDefense(int incomingDamage) {
        int finalDamage = Math.max(0, incomingDamage - this.defense);
        setHealth(this.health - finalDamage);
    }

    public abstract void attack(GameCharacter target);

    public abstract void specialAbility(GameCharacter target);

    public void updateStatuses() {
        for (StatusEffect effect : statusEffects) {
            if (effect.isType("Burning")) {
                int burnDamage = effect.getIntensity();
                System.out.println(getName() + " takes " + burnDamage
                        + " burn damage from " + effect.getEffectName() + "!");
                setHealth(this.health - burnDamage);
            }
        }
        statusEffects.removeIf(effect -> {
            effect.reduceDuration();
            return effect.isExpired();
        });
    }

    public String getStatuses() {
        for (StatusEffect effect : statusEffects) {
            if (effect.isType("Burning")) {
                int burnDamage = effect.getIntensity();
                setHealth(this.health - burnDamage);
                return getName() + " takes " + burnDamage
                        + " burn damage from " + effect.getEffectName() + "!";
            }
        }
        statusEffects.removeIf(effect -> {
            effect.reduceDuration();
            return effect.isExpired();
        });
        return "";
    }

    public void updateTurn() {
        updateStatuses();
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    // --- Setters ---
    public void setName(String name)       { this.name = name; }
    public void setAttack(int attack)      { this.attack = attack; }
    public void setDefense(int defense)    { this.defense = defense; }

    public void setHealth(int h) {
        if (h < 0)           this.health = 0;
        else if (h > maxHealth) this.health = maxHealth;
        else                 this.health = h;
    }

    public void setMaxHealth(int max) {
        this.maxHealth = max;
        if (health > maxHealth) health = maxHealth;
    }

    // --- Getters ---
    public String getName()                        { return name; }
    public int getHealth()                         { return health; }
    public int getMaxHealth()                      { return maxHealth; }
    public int getAttack()                         { return attack; }
    public int getDefense()                        { return defense; }
    public List<StatusEffect> getStatusEffects()   { return statusEffects; }

    public void addStatusEffect(StatusEffect effect) {
        this.statusEffects.add(effect);
    }

    @Override
    public String toString() {
        return getName() + " [HP: " + getHealth() + "/" + getMaxHealth() + "]";
    }
}
