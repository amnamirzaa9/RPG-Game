package rpg.core;

import rpg.utils.DamageCalculator;

/**
 * @author Amna Mirza
 */
public class bat extends Enemy {

    public bat() {
        super("Bat", 80, 10, 8, 40, 30);
    }

    @Override
    public void attack(GameCharacter target) {
        System.out.println(getName() + "Uses night vision lasers!");
        int finalDamage = DamageCalculator.calculateFinalDamage(this.getAttack());
        target.applyDefense(finalDamage);
    }

    @Override
    public void specialAbility(GameCharacter target) {
        System.out.println(getName() + " uses GROUND SLAM!");
        int slamDamage = (int)(this.getAttack() * 1.5);
        target.applyDefense(slamDamage);
    }
}