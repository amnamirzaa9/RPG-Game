package rpg.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main game controller — turn-based RPG loop.
 * @author Muhammad Afrasyab Asif
 */
public class Game {

    private GameCharacter player;
    private int totalGold  = 0;
    private int totalExp   = 0;
    private int turnNumber = 0;

    private final Scanner scanner = new Scanner(System.in);

    // ── Entry point ──────────────────────────────────────────────────────────

    public static void main(String[] args) {
        new Game().start();
    }

    public void start() {
        printBanner();
        player = chooseCharacter();

        List<Enemy> enemies = buildEnemyWaves();

        System.out.println("\n========================================");
        System.out.println("  Your quest begins, " + player.getName() + "!");
        System.out.println("========================================\n");

        for (Enemy enemy : enemies) {
            if (!player.isAlive()) break;

            boolean survived = runBattle(enemy);
            if (!survived) break;

            totalGold += enemy.getGoldReward();
            totalExp  += enemy.getExpReward();
            printRewards(enemy);

            if (enemies.indexOf(enemy) < enemies.size() - 1) {
                System.out.println("\nPress ENTER to face the next enemy...");
                scanner.nextLine();
            }
        }

        printGameOver();
    }

    // ── Character selection ───────────────────────────────────────────────────

    private GameCharacter chooseCharacter() {
        System.out.println("Enter your hero's name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) name = "Hero";

        System.out.println("\nChoose your class:");
        System.out.println("  [1] Warrior  — 150 HP | 15 ATK | 10 DEF  (Double Strike special)");
        System.out.println("  [2] Mage     —  80 HP | 25 ATK |  5 DEF  (Arcane Shield special)");

        int choice = readInt(1, 2);
        GameCharacter chosen = (choice == 1) ? new Warrior(name) : new Mage(name);

        System.out.println("\nWelcome, " + chosen.getName()
                + " the " + (choice == 1 ? "Warrior" : "Mage") + "!\n");
        return chosen;
    }

    // ── Enemy waves ───────────────────────────────────────────────────────────

    private List<Enemy> buildEnemyWaves() {
        List<Enemy> waves = new ArrayList<>();
        waves.add(new Goblin());
        waves.add(new bat());
        waves.add(new Dragon());
        return waves;
    }

    // ── Battle loop ───────────────────────────────────────────────────────────

    public boolean runBattle(Enemy enemy) {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.printf ("║  ⚔  A %s appears!%n", enemy.getName());
        System.out.println("╚══════════════════════════════════════╝");
        printStats(enemy);

        while (player.isAlive() && enemy.isAlive()) {
            turnNumber++;
            System.out.println("\n──────────── Turn " + turnNumber + " ────────────");
            printStats(player);
            printStats(enemy);

            // Player turn
            playerTurn(enemy);
            if (!enemy.isAlive()) break;

            // Enemy turn
            System.out.println("\n--- " + enemy.getName() + "'s turn ---");
            EnemyAI.takeTurn(enemy, player);

            // End-of-turn upkeep
            player.updateTurn();
            enemy.updateTurn();
            printActiveEffects(player);
        }

        if (player.isAlive()) {
            System.out.println("\n✅ You defeated the " + enemy.getName() + "!");
            return true;
        } else {
            System.out.println("\n💀 You were slain by the " + enemy.getName() + "...");
            return false;
        }
    }

    // ── Player turn ───────────────────────────────────────────────────────────

    private void playerTurn(Enemy enemy) {
        System.out.println("\n--- Your turn ---");
        System.out.println("What will you do?");
        System.out.println("  [1] Attack");
        System.out.println("  [2] Special Ability");
        System.out.println("  [3] View Status Effects");

        int choice = readInt(1, 3);

        switch (choice) {
            case 1 -> {
                System.out.println("\n⚔  " + player.getName() + " attacks!");
                player.attack(enemy);
            }
            case 2 -> {
                System.out.println();
                player.specialAbility(enemy);
            }
            case 3 -> {
                printActiveEffects(player);
                playerTurn(enemy); // let player act after viewing
            }
        }
    }

    // ── UI helpers ────────────────────────────────────────────────────────────

    private void printBanner() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║         ⚔  RPG ADVENTURE ⚔           ║");
        System.out.println("║                                      ║");
        System.out.println("╚══════════════════════════════════════╝\n");
    }

    private void printStats(GameCharacter c) {
        int hp    = c.getHealth();
        int maxHp = c.getMaxHealth();
        int barLen = 20;
        int filled = (int)((double) hp / maxHp * barLen);
        String bar = "█".repeat(filled) + "░".repeat(barLen - filled);
        System.out.printf("  %-16s HP [%s] %d/%d%n", c.getName(), bar, hp, maxHp);
    }

    private void printActiveEffects(GameCharacter c) {
        if (c.getStatusEffects().isEmpty()) return;
        System.out.println("\n  🔥 " + c.getName() + "'s active effects:");
        for (StatusEffect effect : c.getStatusEffects()) {
            System.out.println("     • " + effect.getEffectName()
                    + " (" + effect.getDuration() + " turns left"
                    + ", intensity: " + effect.getIntensity() + ")");
        }
    }

    private void printRewards(Enemy enemy) {
        System.out.println("\n💰 Rewards:");
        System.out.println("   Gold : +" + enemy.getGoldReward() + "  (Total: " + totalGold + ")");
        System.out.println("   XP   : +" + enemy.getExpReward()  + "  (Total: " + totalExp  + ")");
    }

    private void printGameOver() {
        System.out.println("\n╔══════════════════════════════════════╗");
        if (player.isAlive()) {
            System.out.println("║     🏆  VICTORY! QUEST COMPLETE       ║");
        } else {
            System.out.println("║          💀  GAME OVER                ║");
        }
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf ("║  Hero    : %-27s║%n", player.getName());
        System.out.printf ("║  Turns   : %-27d║%n", turnNumber);
        System.out.printf ("║  Gold    : %-27d║%n", totalGold);
        System.out.printf ("║  XP      : %-27d║%n", totalExp);
        System.out.println("╚══════════════════════════════════════╝");
    }

    private int readInt(int min, int max) {
        while (true) {
            System.out.print("Enter choice (" + min + "-" + max + "): ");
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val >= min && val <= max) return val;
                System.out.println("  Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input. Please enter a number.");
            }
        }
    }
}
