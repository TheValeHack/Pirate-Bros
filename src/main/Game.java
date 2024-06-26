package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;
import tiles.TileManager;

public class Game implements Runnable {

    private final GameWindow gameWindow;
    private final GamePanel gamePanel;
    private Thread gameLoopThread;
    private static final int TARGET_FPS = 120;
    private static final int TARGET_UPS = 200;
    
    private Playing playing;
	private Menu menu;

    public Game() {
        gamePanel = new GamePanel(this);
        
        menu = new Menu(this);
		playing = new Playing(this);

		
        
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();

        startGameLoop();
    }

    private void startGameLoop() {
        gameLoopThread = new Thread(this);
        gameLoopThread.start();
    }

    public void updateGame() {
    	switch (Gamestate.state) {
		case MENU:
			menu.update();
			break;
		case PLAYING:
			playing.update();
			break;
		case OPTIONS:
		case QUIT:
		default:
			System.exit(0);
			break;

		}
    }

    public void renderGame(Graphics g) {
    	switch (Gamestate.state) {
		case MENU:
			menu.draw(g);
			break;
		case PLAYING:
			playing.draw(g);
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
        if (Gamestate.state == Gamestate.PLAYING) {
        	playing.getPlayer().resetDirBooleans();
        	playing.getEnemy().resetDirBooleans();
        }
    }
    
//    private void updateBombs() {
//        Iterator<Bomb> iterator = bombs.iterator();
//        while (iterator.hasNext()) {
//            Bomb bomb = iterator.next();
//            bomb.update();
//            if (bomb.hasExploded()) {
//                iterator.remove();
//            }
//        }
//    }
//    private void renderBombs(Graphics g) {
//        Iterator<Bomb> iterator = bombs.iterator();
//        while (iterator.hasNext()) {
//            Bomb bomb = iterator.next();
//            bomb.render(g);
//        }
//    }
//
//    public void addBomb(Bomb bomb) {
//        bombs.add(bomb);
//    }
//
//    public void removeBomb(Bomb bomb) {
//        bombs.remove(bomb);
//    }
//    
//    public Bomber getPlayer() {
//        return player;
//    }
    
//    public Captain getEnemy() {
//        return enemy;
//    }
//    public BigGuy getEnemy() {
//        return enemy;
//    }
//    public BaldPirate getEnemy() {
//        return enemy;
//    }
//    public Cucumber getEnemy() {
//        return enemy;
//    }
//    public Whale getEnemy() {
//        return enemy;
//    }
    public Menu getMenu() {
		return menu;
	}

	public Playing getPlaying() {
		return playing;
	}
    public TileManager getTileManager() {
        return playing.getTileManager();
    }
    public GamePanel getGamePanel() {
    	return gamePanel;
    }
}
