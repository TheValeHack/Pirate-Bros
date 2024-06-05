package main;

import java.awt.Graphics;
import players.Bomber;

public class Game implements Runnable {

    private final GameWindow gameWindow;
    private final GamePanel gamePanel;
    private Thread gameLoopThread;
    private static final int TARGET_FPS = 120;
    private static final int TARGET_UPS = 200;

    private final Bomber player;

    public Game() {
        player = new Bomber(200, 200);

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();

        startGameLoop();
    }

    private void startGameLoop() {
        gameLoopThread = new Thread(this);
        gameLoopThread.start();
    }

    public void updateGame() {
        player.update();
    }

    public void renderGame(Graphics g) {
        player.render(g);
    }

    @Override
    public void run() {
        final double timePerFrame = 1_000_000_000.0 / TARGET_FPS;
        final double timePerUpdate = 1_000_000_000.0 / TARGET_UPS;

        long previousTime = System.nanoTime();
        long lastCheck = System.currentTimeMillis();

        int frameCount = 0;
        int updateCount = 0;
        double deltaUpdate = 0;
        double deltaFrame = 0;

        while (true) {
            long currentTime = System.nanoTime();
            deltaUpdate += (currentTime - previousTime) / timePerUpdate;
            deltaFrame += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaUpdate >= 1) {
                updateGame();
                updateCount++;
                deltaUpdate--;
            }

            if (deltaFrame >= 1) {
                gamePanel.repaint();
                frameCount++;
                deltaFrame--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frameCount + " | UPS: " + updateCount);
                frameCount = 0;
                updateCount = 0;
            }
        }
    }

    public void handleWindowFocusLost() {
        player.resetDirBooleans();
    }

    public Bomber getPlayer() {
        return player;
    }
}
