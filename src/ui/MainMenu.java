package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import main.Game;
import main.GamePanel;

public class MainMenu {
    private static final String[] menuOptions = { "Start Game", "Instructions", "Exit" };
    private static final Font font = new Font("Arial", Font.BOLD, 24);
    private static int currentOption = 0;

    public static void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        g.setFont(font);
        for (int i = 0; i < menuOptions.length; i++) {
            if (i == currentOption) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(menuOptions[i], Game.WIDTH / 2 - 75, 150 + i * 50);
        }
    }

    public static void selectOption(GamePanel gamePanel) {
        switch (currentOption) {
            case 0:
                // Start Game
                gamePanel.getGame().setInMainMenu(false);
                break;
            case 1:
                // Instructions
                // Implement your logic to show game instructions
                break;
            case 2:
                // Exit
                System.exit(0);
                break;
            default:
                break;
        }
    }

    public static void keyPressed(int keyCode, GamePanel gamePanel) {
        if (keyCode == KeyEvent.VK_UP) {
            currentOption = (currentOption - 1 + menuOptions.length) % menuOptions.length;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            currentOption = (currentOption + 1) % menuOptions.length;
        } else if (keyCode == KeyEvent.VK_ENTER) {
            selectOption(gamePanel);
        }
    }
    
}
