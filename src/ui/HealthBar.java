package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import main.Game;

public class HealthBar {
    private Game game;
    private BufferedImage healthBarImage;
    private BufferedImage heartImage;
    private int maxHealth;
    private int currentHealth;
     public HealthBar(Game game) {
        this.game = game;
        this.maxHealth = 3;
        this.currentHealth = 3;
        loadHealthBarAssets();
    }

    private void loadHealthBarAssets() {
        try (InputStream is = getClass().getResourceAsStream("/Objects-Item/Health Bar/Health Bar.png")) {
            if (is != null) {
                healthBarImage = ImageIO.read(is);
            } else {
                System.err.println("Health bar image not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (InputStream is = getClass().getResourceAsStream("/Objects-Item/Health Bar/Heart.png")) {
            if (is != null) {
                heartImage = ImageIO.read(is);
            } else {
                System.err.println("Heart image not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateHealth(int healthChange) {
        this.currentHealth = Math.max(0, Math.min(maxHealth, this.currentHealth + healthChange));
    }

    public void render(Graphics g) {
        if (healthBarImage == null || heartImage == null) {
            return; // Skip rendering if images are not loaded
        }

        int heartWidth = heartImage.getWidth();
        int heartHeight = heartImage.getHeight();
        int spacing = 10; // Space between hearts
        int startX = 10;  // Starting X position
        int startY = 10;  // Starting Y position

        for (int i = 0; i < maxHealth; i++) {
            if (i < currentHealth) {
                g.drawImage(heartImage, startX + i * (heartWidth + spacing), startY, null);
            } else {
                g.drawImage(heartImage, startX + i * (heartWidth + spacing), startY, heartWidth, heartHeight, null);
            }
        }
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
}
