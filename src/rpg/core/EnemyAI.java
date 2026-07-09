package rpg.core;

import java.util.Random;

/**
 * Simple AI controller for enemies.
 * @author Muhammad Afrasyab Asif
 */
public class EnemyAI {

    private static final Random random = new Random();

    private static final double SPECIAL_CHANCE = 0.25;
    private static final double DEFEND_CHANCE  = 0.15;

    public static void takeTurn(Enemy enemy, GameCharacter player) {
        double roll = random.nextDouble();

        if (roll < SPECIAL_CHANCE) {
            System.out.println("\n  [AI] " + enemy.getName() + " winds up for a special move!");
            enemy.specialAbility(player);

        } else if (roll < SPECIAL_CHANCE + DEFEND_CHANCE) {
            System.out.println("\n  [AI] " + enemy.getName() + " takes a defensive stance and recovers slightly.");
            int recovered = (int)(enemy.getMaxHealth() * 0.10);
            enemy.setHealth(enemy.getHealth() + recovered);
            System.out.println("       " + enemy.getName() + " recovers " + recovered + " HP! ("
                    + enemy.getHealth() + "/" + enemy.getMaxHealth() + ")");

        } else {
            enemy.attack(player);
        }
    }

    public static String takeTurngui(Enemy enemy, GameCharacter player) {
       final Random rand = new Random();
        double roll = rand.nextDouble();

        if (roll < SPECIAL_CHANCE) {
            enemy.specialAbility(player);
            return enemy.getName() + " winds up for a special move!";

        } else if (roll < SPECIAL_CHANCE + DEFEND_CHANCE) {
            int recovered = (int)(enemy.getMaxHealth() * 0.10);
            enemy.setHealth(enemy.getHealth() + recovered);
            return enemy.getName() + " takes a defensive stance and recovers slightly."
                    + enemy.getName() + " recovers " + recovered + " HP! ("
                    + enemy.getHealth() + "/" + enemy.getMaxHealth() + ")";

        } else {
            enemy.attack(player);
            return enemy.getName()+" attacked using no power ups";
        }
    }
}
