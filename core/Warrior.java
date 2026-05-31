package rpg.core;

import rpg.utils.DamageCalculator;

/**
 * @author Amna Mirza
 */
public class Warrior extends GameCharacter {

    int specialCooldown = 0;

    public Warrior(String name) {
        setName(name);
        setMaxHealth(150);
        setHealth(150);
        setAttack(15);
        setDefense(10);
    }

    @Override
    public void specialAbility(GameCharacter target) {
        if (specialCooldown == 0) {
            System.out.println(getName() + " uses DOUBLE STRIKE!");
            int finalDamage = DamageCalculator.calculateFinalDamage(getAttack() * 2);
            target.applyDefense(finalDamage);
            specialCooldown = 3;
        } else {
            System.out.println("Special ability on cooldown for " + specialCooldown + " more turns.");
        }
    }

    @Override
    public void updateTurn() {
        super.updateTurn();
        if (specialCooldown > 0) specialCooldown--;
    }

    @Override
    public void attack(GameCharacter target) {
        System.out.println(getName() + " strikes with a sword!");
        int finalDamage = DamageCalculator.calculateFinalDamage(getAttack());
        target.applyDefense(finalDamage);
    }
}
