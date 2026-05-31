package rpg.core;

/**
 * @author Amna Mirza
 */
public abstract class Enemy extends GameCharacter {

    private int goldReward;
    private int expReward;

    public Enemy(String name, int health, int attack, int defense,
                 int goldReward, int expReward) {
        setName(name);
        setMaxHealth(health);
        setHealth(health);
        setAttack(attack);
        setDefense(defense);
        this.goldReward = goldReward;
        this.expReward  = expReward;
    }

    public int getGoldReward() { return goldReward; }
    public int getExpReward()  { return expReward; }

    @Override
    public abstract void attack(GameCharacter target);
}
