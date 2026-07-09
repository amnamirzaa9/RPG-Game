package rpg.gui;

import rpg.utils.imageload;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {
    private Image backgroundImage;

    public MainMenu(MainGUI frame,String resourcePath) {

        JPanel inner =new JPanel(new GridLayout(5, 1, 10, 10));
        inner.setOpaque(false);

        setBackground(Color.DARK_GRAY);
        setLayout(new FlowLayout());
        JLabel title = new JLabel("MAIN MENU");
        title.setFont(new Font("Arial", Font.BOLD, 24));


        JButton startBtn = imageload.createbutton("/rpg/assets/Start.png",150,75);
        JButton settingsBtn = imageload.createbutton("/rpg/assets/Settings.png",150,75);
        JButton exitBtn = imageload.createbutton("/rpg/assets/Exit.png",150,75);

        startBtn.addActionListener(e -> frame.showView("Game"));
        settingsBtn.addActionListener(e -> frame.showView("Settings"));
        exitBtn.addActionListener(e -> System.exit(0));

        inner.add(title);
        inner.add(startBtn);
        inner.add(settingsBtn);
        inner.add(exitBtn);
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