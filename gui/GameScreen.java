package rpg.gui;

import rpg.core.*;
import rpg.utils.imageload;
import rpg.utils.projectile;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameScreen extends JPanel{
    private ImageIcon background;

    Image playerGif,enemyGif;
    JButton attackBtn,specialBtn,nextBtn,potionBtn;
    private GameCharacter player;
    Enemy enemy;
    String playerName,enemyName;
    int playertype,playerHP,playerMaxHP,enemyHP,enemyMaxHP,round=0;
    private int playerX = 50, playerY = 150;
    int enemyX= 500,enemyY=140,enemywidth=180,enemyhieght=180;
    private String dialogueText = "";
    private GameLoop gameLoop;
    List<Enemy> enemies;
    int cooldown,totalgold,potioncool;
    Image activeAnimation,potionanim,projanim;
    private List<projectile> projectiles = new ArrayList<>();
    private Image projectileImg;

    public GameScreen(MainGUI frame) {

        enemies= buildEnemyWaves();
        System.out.println(enemies);
        this.enemy= enemies.get(round);
        enemyName=enemy.getName();

        playerGif = new ImageIcon(getClass().getResource("/rpg/assets/mage.gif")).getImage();
        enemyGif = new ImageIcon(getClass().getResource("/rpg/assets/goblin.gif")).getImage();
        player=new Warrior("");

        activeAnimation = new ImageIcon(getClass().getResource("/rpg/assets/empty.png")).getImage();
        potionanim = new ImageIcon(getClass().getResource("/rpg/assets/empty.png")).getImage();
        projanim = new ImageIcon(getClass().getResource("/rpg/assets/empty.png")).getImage();
        projectileImg =new ImageIcon(getClass().getResource("/rpg/assets/ball.png")).getImage();


        setLayout(new BorderLayout());
        setOpaque(false); // Allows the background image to show through

        // Create the Button Container
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        actionPanel.setOpaque(false); // Transparent so it doesn't block the HUD graphics

        // 1. Attack Button
        attackBtn = createGameButton("Attack", Color.RED);
        attackBtn.addActionListener(e -> performAttack());

        // 2. Special Ability Button
        specialBtn = createGameButton("Surprise!", Color.ORANGE);
        specialBtn.addActionListener(e -> performSpecial());

        // 3. Next Enemy Button
        nextBtn = createGameButton("Next Enemy", Color.CYAN);
        nextBtn.setEnabled(false);
        nextBtn.addActionListener(e -> spawnNextEnemy());

        potionBtn = createGameButton("Drink potion", Color.RED);
        nextBtn.setEnabled(false);
        potionBtn.addActionListener(e -> drinkpotion());

        // Add a margin to push buttons into the HUD box area
        actionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 40));

        actionPanel.add(potionBtn);
        actionPanel.add(attackBtn);
        actionPanel.add(specialBtn);
        actionPanel.add(nextBtn);

        add(actionPanel, BorderLayout.SOUTH);

        background= imageload.loadimage("/rpg/assets/background.jpg");
        setFocusable(true);

        this.gameLoop = new GameLoop(this);
        this.gameLoop.start();
    }

    private void drinkpotion() {
        if(potioncool<1){
            potioncool=5;
            if(!Objects.equals(potionBtn.getText(), "Missile blast")){
                potionanim = new ImageIcon(getClass().getResource("/rpg/assets/Dark-Bolt.gif")).getImage();
                player.setHealth(player.getHealth()+40);
                dialogueText = EnemyAI.takeTurngui(enemy, player);
                Timer cooldown = new Timer(800, e -> {
                    potionanim = new ImageIcon(getClass().getResource("/rpg/assets/empty.png")).getImage();
                });
                cooldown.setRepeats(false); // Critical for one-time events
                cooldown.start();
            }else{
                Timer cooldown = new Timer(1200, e -> {
                    projanim = new ImageIcon(getClass().getResource("/rpg/assets/Fire-bomb.gif")).getImage();
                    enemy.setHealth(enemy.getHealth()-35);
                    dialogueText = EnemyAI.takeTurngui(enemy, player);
                    Timer cooldown1 = new Timer(1500, f -> {
                        projanim = new ImageIcon(getClass().getResource("/rpg/assets/empty.png")).getImage();
                    });
                    cooldown1.setRepeats(false);
                    cooldown1.start();

                });
                cooldown.setRepeats(false); // Critical for one-time events
                cooldown.start();
                throwProjectile();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);

        if (playerGif != null) {
            g2d.drawImage(playerGif, playerX, playerY, 128, 128, this);
        }
        if (enemyGif != null && enemyHP > 0) {
            g2d.drawImage(enemyGif, enemyX, enemyY, enemywidth, enemyhieght, this);
        }

        g.drawImage(activeAnimation, enemyX, enemyY, 150, 150, this);
        g.drawImage(potionanim,playerX,playerY, 150, 150, this);
        g.drawImage(projanim,enemyX,enemyY, 150, 150, this);

        // Draw all active projectiles
        for (projectile p : projectiles) {
            g2d.drawImage(projectileImg, p.x, p.y, 40, 40, this);
        }

        drawHUD(g2d);
    }

    private void throwProjectile() {
        // Start the projectile at the Wizard's hand position
        projectiles.add(new projectile(150, 220));
    }

    // Called by your GameLoop class
    public void updateProjectiles() {
        for (int i = 0; i < projectiles.size(); i++) {
            projectile p = projectiles.get(i);
            p.update();
            if (!p.active) {
                projectiles.remove(i);
                i--;
            }
        }
    }


    private void drawHUD(Graphics2D g2d) {
        int margin = 20;
        int hudHeight = 150;
        int yStart = getHeight() - hudHeight - margin;

        g2d.setColor(new Color(20, 20, 20, 220));
        g2d.fillRoundRect(margin, yStart, getWidth() - (margin * 2), hudHeight, 15, 15);

        g2d.setFont(new Font("Serif", Font.ITALIC, 20)); // RPGs look great with Serif fonts
        g2d.setColor(Color.WHITE);
        // Draw the current dialogue string near the top of the HUD
        g2d.drawString(dialogueText, margin + 20, yStart + 15);

        drawStatBar(g2d, margin + 30, yStart + 50, playerName, playerHP, playerMaxHP, Color.GREEN);

        int enemyX = getWidth() - margin - 230;
        drawStatBar(g2d, enemyX, yStart + 50, enemyName, enemyHP, enemyMaxHP, Color.RED);
    }

    private void drawStatBar(Graphics2D g2d, int x, int y, String name, int current, int max, Color barColor) {
        int barWidth = 200;
        int barHeight = 20;

        double healthPercent = (double) current / max;
        int fillWidth = (int) (healthPercent * barWidth);

        g2d.setColor(Color.WHITE);
        g2d.drawString(name, x, y - 5);

        g2d.setColor(new Color(50, 50, 50));
        g2d.fillRect(x, y, barWidth, barHeight);

        g2d.setColor(barColor);
        g2d.fillRect(x, y, fillWidth, barHeight);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 12));
        g2d.drawString(current + "/" + max, x + 5, y + 15);
    }

    private java.util.List<Enemy> buildEnemyWaves() {
        List<Enemy> waves = new ArrayList<>();
        waves.add(new Goblin());
        waves.add(new bat());
        waves.add(new Dragon());
        return waves;
    }

    private void updatestats() {
        playerHP = player.getHealth();
        playerMaxHP = player.getMaxHealth();
        enemyHP=enemy.getHealth();
        enemyMaxHP = enemy.getMaxHealth();
        enemyName = enemy.getName();

    }

    public void getplayerinfo(String name,int type){
        playerName=name;
        playertype = type;
        if (playertype==0){
            player=new Warrior(playerName);
            potionBtn.setText("Missile blast");
            playerGif=new ImageIcon(getClass().getResource("/rpg/assets/warrior.gif")).getImage();
        }else{
            player= new Mage(playerName);
        }
    }

    private JButton createGameButton(String text, Color hoverColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusable(false);
        btn.setBackground(new Color(50, 50, 50));
        btn.setForeground(Color.WHITE);
        return btn;
    }

    private void performAttack() {
        if(enemy.getHealth()>0){
            attackBtn.setEnabled(false);
            specialBtn.setEnabled(false);
            player.attack(enemy);
            dialogueText = EnemyAI.takeTurngui(enemy, player);
            attackBtn.setEnabled(true);
            specialBtn.setEnabled(true);
            cooldown=0;
            activeAnimation = new ImageIcon(getClass().getResource("/rpg/assets/vfx_light1.gif")).getImage();
            Timer cooldown = new Timer(800, e -> {
                activeAnimation = new ImageIcon(getClass().getResource("/rpg/assets/empty.png")).getImage();
            });
            cooldown.setRepeats(false); // Critical for one-time events
            cooldown.start();
        }else{
            attackBtn.setEnabled(false);
            specialBtn.setEnabled(false);
            nextBtn.setEnabled(true);
            totalgold+= enemy.getGoldReward();
            dialogueText="   Gold : +" + enemy.getGoldReward() + "  (Total: " +  totalgold+ ")";
        }
        potioncool--;
    }

    private void performSpecial() {
        if(enemy.getHealth()>0) {
            if(playertype==0){
                if(cooldown==0){
                    cooldown=1;
                    specialBtn.setEnabled(false);
                }else{
                    specialBtn.setEnabled(true);
                }
            }
            attackBtn.setEnabled(false);
            player.specialAbility(enemy);
            dialogueText = EnemyAI.takeTurngui(enemy, player);
            attackBtn.setEnabled(true);
        }else{
            attackBtn.setEnabled(false);
            specialBtn.setEnabled(false);
            nextBtn.setEnabled(true);
            totalgold+= enemy.getGoldReward();
            dialogueText="   Gold : +" + enemy.getGoldReward() + "  (Total: " +  totalgold+ ")";
        }
        potioncool--;
    }

    private void spawnNextEnemy() {
        round++;
        enemy= enemies.get(round);
        if(round==1){
            enemywidth=100;enemyhieght=100;
            enemyGif = new ImageIcon(getClass().getResource("/rpg/assets/bat.gif")).getImage();
        }
        else if(round==2){
            enemywidth=180;enemyhieght=180;
            enemyGif = new ImageIcon(getClass().getResource("/rpg/assets/dragon.gif")).getImage();
        }
        attackBtn.setEnabled(true);
        specialBtn.setEnabled(true);
        nextBtn.setEnabled(false);
    }

    public void winorlose(){
        if(round==2) {
            if(playerHP==0&&enemyHP==0){
                attackBtn.setEnabled(false);
                specialBtn.setEnabled(false);
                nextBtn.setEnabled(false);
                dialogueText="Gold is route of evil: "+ totalgold;
                JOptionPane.showMessageDialog(this, "Just Y you kill each other?",
                        "Egoistic!",
                        JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            } else if(enemyHP ==0){
                attackBtn.setEnabled(false);
                specialBtn.setEnabled(false);
                nextBtn.setEnabled(false);
                dialogueText="Gold after torment:"+ totalgold;
                JOptionPane.showMessageDialog(this, "How can you win?", "Unbelievable!",
                        JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }
        if(playerHP==0){
            attackBtn.setEnabled(false);
            specialBtn.setEnabled(false);
            nextBtn.setEnabled(false);
            dialogueText="Gold after get rekt:"+ totalgold;
            JOptionPane.showMessageDialog(this, "You lost.Skill Issue", "OOP is your future",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    public void update() {
        updatestats();
        updateProjectiles();
        winorlose();
    }

}