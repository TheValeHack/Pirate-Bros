// Overlay.java
package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class Overlay {
    private boolean gameOver;
    private String winner;

    public Overlay() {
        this.gameOver = false;
    }

    public void setGameOver(String winner) {
        this.gameOver = true;
        this.winner = winner;
    }

    public void resetGame() {
        this.gameOver = false;
    }

    public void draw(Graphics g) {
        if (gameOver) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, 800, 600); // Assuming game window size of 800x600

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString(winner + " wins!", 250, 250);

            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.drawString("Click to Restart", 280, 350);
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void handleMouseClick(MouseEvent e) {
        if (gameOver) {
            int x = e.getX();
            int y = e.getY();
            // Check if click is within the "Restart" button area
            if (x >= 280 && x <= 520 && y >= 320 && y <= 380) {
                resetGame();
            }
        }
    }
}
