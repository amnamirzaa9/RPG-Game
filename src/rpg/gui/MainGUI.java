package rpg.gui;

import rpg.utils.Soundmanager;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {
    private CardLayout card = new CardLayout();
    private JPanel main = new JPanel(card);

    public MainGUI() {
        setTitle("RPG Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 520);
        setLocationRelativeTo(null);

        Soundmanager sound = new Soundmanager();

        sound.playBackgroundMusic("/rpg/assets/sound.wav");

        MainMenu mainMenu = new MainMenu(this,"/rpg/assets/background.jpg");
        GameScreen game= new GameScreen(this);
        GameMenu gameMenu = new GameMenu(this,"/rpg/assets/background.jpg",game);
        SettingsMenu settingsMenu = new SettingsMenu(this,sound,"/rpg/assets/background.jpg");

        main.add(mainMenu, "Main");
        main.add(gameMenu, "Game");
        main.add(settingsMenu, "Settings");
        main.add(game,"actualgame");

        add(main);
        setVisible(true);
    }

    public void showView(String viewName) {
        card.show(main, viewName);
    }

    public static void main(String[] args) {
        UIManager.put("Label.foreground", Color.WHITE);
        UIManager.put("TextField.foreground", Color.WHITE);
        SwingUtilities.invokeLater(()-> new MainGUI());
    }
}