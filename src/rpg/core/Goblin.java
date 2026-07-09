package rpg.core;

import rpg.utils.DamageCalculator;

/**
 * @author Amna Mirza
 */
public class Goblin extends Enemy {

    public Goblin() {
        super("Goblin", 50, 5, 2, 15, 10);
    }

    @Override
    public void attack(GameCharacter target) {
        System.out.println(getName() + " lunges with a rusty dagger!");
        int finalDamage = DamageCalculator.calculateFinalDamage(this.getAttack());
        target.applyDefense(finalDamage);
    }

    @Override
    public void specialAbility(GameCharacter target) {
        System.out.println(getName() + " performs a sneaky Quick Strike!");
        target.applyDefense(5);
    }
}
