package players;

import java.awt.Graphics;
import java.awt.Rectangle;
import main.GamePanel;

public abstract class Player {
    protected float x;
    protected float y;
    protected float initialX;
    protected float initialY;
    protected Rectangle solidArea;
    protected GamePanel gamePanel;
    protected int maxHealth;
    protected int currentHealth;
    protected boolean alive;
    protected Player enemy;
    protected boolean facingLeft = false;
    protected boolean isDying = false;
    protected boolean deathAnimationDone = false;

    public Player(float x, float y, int maxHealth, GamePanel gamePanel) {
        this.x = x;
        this.y = y;
        this.initialX = x;
        this.initialY = y;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.gamePanel = gamePanel;
        this.alive = true;
    }
    
    public void setEnemy(Player enemy) {
        this.enemy = enemy;
    }

    public void takeDamage(int damage) {
        if (!isDying) {
            currentHealth -= damage;
            if (currentHealth <= 0) {
                currentHealth = 0;
                alive = false;
                isDying = true;
            }
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

    public boolean isDying() {
        return isDying;
    }

    public boolean isDeathAnimationDone() {
        return deathAnimationDone;
    }

    public void setDeathAnimationDone(boolean done) {
        this.deathAnimationDone = done;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void reset() {
        this.x = initialX;
        this.y = initialY;
        this.currentHealth = maxHealth;
        this.alive = true;
        this.isDying = false;
        this.deathAnimationDone = false;
    }

    public abstract void update();
    public abstract void render(Graphics g);
}
