package rpg.core;

import rpg.utils.DamageCalculator;

/**
 * @author Amna Mirza
 */
public class Mage extends GameCharacter {

    public Mage(String name) {
        setName(name);
        setMaxHealth(80);
        setHealth(80);
        setAttack(25);
        setDefense(5);
    }

    @Override
    public void specialAbility(GameCharacter target) {
        System.out.println(getName() + " casts Arcane Shield!");
        this.addStatusEffect(new StatusEffect("Shielded", 2, 0));
    }

    @Override
    public void attack(GameCharacter target) {
        System.out.println(getName() + " fires a Magic Bolt!");
        int finalDamage = DamageCalculator.calculateFinalDamage(getAttack());
        target.applyDefense(finalDamage);
    }
}
