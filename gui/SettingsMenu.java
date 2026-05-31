package rpg.gui;

import rpg.utils.Soundmanager;
import rpg.utils.imageload;

import javax.swing.*;
import java.awt.*;

public class SettingsMenu extends JPanel {

    private Image backgroundImage;

    public SettingsMenu(MainGUI frame, Soundmanager sound,String resourcePath) {

        JPanel inner =new JPanel(new GridLayout(5, 1, 10, 10));
        inner.setOpaque(false);

        setBackground(Color.DARK_GRAY);
        setLayout(new FlowLayout());

        JLabel label = new JLabel("Settings", SwingConstants.CENTER);
        label.setFont(new Font("Serif", 1, 32));
        JSlider volumeSlider = new JSlider(0, 100, 50);
        volumeSlider.addChangeListener(e -> sound.setVolume((float)volumeSlider.getValue()/200));
        volumeSlider.setOpaque(false);
        JButton backBtn = imageload.createbutton("/rpg/assets/Back.png",150,75);

        backBtn.addActionListener(e -> frame.showView("Main"));

        JLabel vol= new JLabel("Volume:");
        vol.setFont(new Font("Serif", 1, 32));

        inner.add(label);
        inner.add(vol);
        inner.add(volumeSlider);
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