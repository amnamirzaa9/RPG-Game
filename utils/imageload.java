package rpg.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class imageload {

    public static ImageIcon loadimage(String path) {
        try {
            URL imgURL = imageload.class.getResource(path);

            if (imgURL == null) {
                System.err.println("Couldn't find file: " + path);
                return null;
            }

            BufferedImage img = ImageIO.read(imgURL);
            return new ImageIcon(img);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static JButton createbutton(String iconPath, int width, int height) {
        ImageIcon icon = imageload.loadimage(iconPath);
        if (icon != null) {
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaledImg);
        }

        JButton button = new JButton(icon);

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        return button;
    }
}