package rpg.gui;

import rpg.utils.imageload;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenu extends JPanel {

    private Image backgroundImage;

    public GameMenu(MainGUI frame,String resourcePath,GameScreen game) {
        JLabel label = new JLabel("GameMenu", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 32));

        JTextField namefield = new JTextField(15);
        namefield.setOpaque(false);
        Font bigFont = new Font("Arial", Font.PLAIN, 24);
        namefield.setFont(bigFont);

        JPanel inner =new JPanel(new GridLayout(5,1,10,10));
        inner.setOpaque(false);

        String s1[] = { "Warrior—150 HP | 15 ATK | 10 DEF (Missile Blast)","Mage—80 HP | 25 ATK |  5 DEF (Health potion)"};
        JComboBox playertype = new JComboBox(s1);
        playertype.setOpaque(false);

        setBackground(Color.DARK_GRAY);
        setLayout(new FlowLayout());

        JButton startBtn = imageload.createbutton("/rpg/assets/Start.png",150,75);
        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.getplayerinfo(namefield.getText(),playertype.getSelectedIndex());
                frame.showView("actualgame");
            }
        });

        JButton backBtn = imageload.createbutton("/rpg/assets/backmenu.png",150,75);
        backBtn.addActionListener(e -> frame.showView("Main"));

        inner.add(label);
        inner.add(namefield);
        inner.add(playertype);
        inner.add(startBtn);
        inner.add(backBtn);
        this.add(inner);

        ImageIcon icon = imageload.loadimage(resourcePath);
            if (icon != null) {
            this.backgroundImage = icon.getImage();
        }
    }

@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g); // Essential to draw the panel properly
    if (backgroundImage != null) {
        // Draw the image to fill the entire panel size
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
}
