package rpg.utils;

public class projectile {
    public int x, y,deltatime;
    public double vO=-15,theta=Math.PI/4,g=-.5;
    public boolean active = true;

    public projectile(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    public void update() {
        deltatime+=1;
        this.x+=-vO*Math.cos(theta);
        this.y+=vO*Math.sin(theta)-g*deltatime;
         // Move right toward the enemy
        if (x > 800) active = false; // Deactivate if it leaves the screen
    }
}