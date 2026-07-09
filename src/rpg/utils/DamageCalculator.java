package rpg.utils;

/**
 * @author Amna Mirza
 */
public class DamageCalculator {

    // FIX: Magic numbers extracted as named constants for readability and easy tuning
    private static final double CRIT_CHANCE = 0.10;       // 10% chance to crit
    private static final double CRIT_MULTIPLIER = 1.5;    // Crits deal 1.5x damage
    private static final double VARIANCE_MIN = 0.90;      // Minimum 90% of base damage
    private static final double VARIANCE_RANGE = 0.20;    // Up to +20% (so max is 110%)

    public static int calculateFinalDamage(int baseAttack) {
        double damage = baseAttack;

        if (Math.random() < CRIT_CHANCE) {
            damage *= CRIT_MULTIPLIER;
            System.out.println("CRITICAL HIT!");
        }

        double variance = VARIANCE_MIN + (Math.random() * VARIANCE_RANGE);
        return (int) (damage * variance);
    }
}
