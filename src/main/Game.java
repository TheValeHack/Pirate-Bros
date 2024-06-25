package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gamestate.GameState;
import gamestate.Menu;
import gamestate.Playing;
import objects.Bomb;
import players.BaldPirate;
import players.Bomber;
import tiles.TileManager;

public class Game implements Runnable {

    private final GameWindow gameWindow;
    private final GamePanel gamePanel;
    private Thread gameLoopThread;
    private static final int TARGET_FPS = 120;
    private static final int TARGET_UPS = 200;
    public static final int WIDTH = 0;
    public static final int HEIGHT = 0;
    private TileManager tileManager;

    private Playing playing;
    private Menu menu;



    private final Bomber player;
//    private final Captain enemy;
//    private final BigGuy enemy;
    private final BaldPirate enemy;
//    private final Cucumber enemy;
//    private final Whale enemy;
    private List<Bomb> bombs;
    

    public Game() {
        gamePanel = new GamePanel(this);
        
        bombs = new ArrayList<>();
        player = new Bomber(150, 200, gamePanel, this);
//        enemy = new Captain(gamePanel.screenWidth - 150, 200, gamePanel, this);
//        enemy = new BigGuy(gamePanel.screenWidth - 150, 200, gamePanel, this);
        enemy = new BaldPirate(gamePanel.screenWidth - 150, 200, gamePanel, this);
//        enemy = new Cucumber(gamePanel.screenWidth - 150, 200, gamePanel, this);
//        enemy = new Whale(gamePanel.screenWidth - 150, 200, gamePanel, this);
        
        player.setEnemy(enemy);
        enemy.setEnemy(player);
        tileManager = new TileManager(gamePanel);

        
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();

        startGameLoop();
    }

    private void startGameLoop() {
        gameLoopThread = new Thread(this);
        gameLoopThread.start();
    }

public void updateGame() {
    switch (GameState.state) {
        case MAIN_MENU:
            // menu.update();
            break;
        case PLAYING:
            player.update();
            enemy.update();
            updateBombs();
            break;
        default:
            break;
    }
}

public void renderGame(Graphics g) {
    switch (GameState.state) {
        case MAIN_MENU:
            // menu.render(g);
            break;
        case PLAYING:
            tileManager.render(g);
            player.render(g);
            enemy.render(g);
            renderBombs(g);
            break;
        default:
            break;
    }
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
        enemy.resetDirBooleans();
    }
    
    private void updateBombs() {
        Iterator<Bomb> iterator = bombs.iterator();
        while (iterator.hasNext()) {
            Bomb bomb = iterator.next();
            bomb.update();
            if (bomb.hasExploded()) {
                iterator.remove();
            }
        }
    }
    private void renderBombs(Graphics g) {
        Iterator<Bomb> iterator = bombs.iterator();
        while (iterator.hasNext()) {
            Bomb bomb = iterator.next();
            bomb.render(g);
        }
    }

    public void addBomb(Bomb bomb) {
        bombs.add(bomb);
    }

    public void removeBomb(Bomb bomb) {
        bombs.remove(bomb);
    }
    
    public Bomber getPlayer() {
        return player;
    }
    
//    public Captain getEnemy() {
//        return enemy;
//    }
//    public BigGuy getEnemy() {
//        return enemy;
//    }
    public BaldPirate getEnemy() {
        return enemy;
    }
//    public Cucumber getEnemy() {
//        return enemy;
//    }
//    public Whale getEnemy() {
//        return enemy;
//    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public boolean isInMainMenu() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isInMainMenu'");
    }
}
