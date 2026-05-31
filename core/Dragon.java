package rpg.core;

import rpg.utils.DamageCalculator;

/**
 * @author Amna Mirza
 */
public class Dragon extends Enemy {

    public Dragon() {
        super("Ancient Dragon", 100, 25, 10, 500, 250);
    }

    @Override
    public void attack(GameCharacter target) {
        System.out.println(getName() + " bites with razor-sharp teeth!");
        int finalDamage = DamageCalculator.calculateFinalDamage(this.getAttack());
        target.applyDefense(finalDamage);
    }

    @Override
    public void specialAbility(GameCharacter target) {
        System.out.println(getName() + " breathes a Torrent of Fire!");
        int finalDamage = DamageCalculator.calculateFinalDamage(25);
        target.applyDefense(finalDamage);
        target.addStatusEffect(new StatusEffect("Burning", 3, 5));
        System.out.println(target.getName() + " is now BURNING!");
    }
}
