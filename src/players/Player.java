package players;

import java.awt.Graphics;
import java.awt.Rectangle;
import main.GamePanel;

public abstract class Player {
    protected float x;
    protected float y;
    protected Rectangle solidArea;
    protected GamePanel gamePanel; 
    protected int maxHealth;
    protected int currentHealth;
    protected boolean alive;
    protected Player enemy;

    public Player(float x, float y, int maxHealth, GamePanel gamePanel) {
        this.x = x;
        this.y = y;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.gamePanel = gamePanel;
        this.alive = true;
    }
    
    public void setEnemy(Player enemy) {
        this.enemy = enemy;
    }

    public void takeDamage(int damage) {
        currentHealth -= damage;
        if (currentHealth <= 0) {
            currentHealth = 0;
            alive = false;
            // Handle player death (e.g., respawn, game over)
        }
    }

    public void heal(int amount) {
        currentHealth += amount;
        if (currentHealth > maxHealth) {
            currentHealth = maxHealth;
        }
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
    
    public Rectangle getSolidArea() {
        return solidArea;
    }

    public boolean isAlive() {
        return alive;
    }
    public float getX() {
    	return x;
    }
    public float getY() {
    	return y;
    }

    public abstract void update();
    public abstract void render(Graphics g);
}
