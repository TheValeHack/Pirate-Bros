package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import players.BaldPirate;
import players.Bomber;
import tiles.TileManager;
import main.Game;
import objects.Bomb;
import ui.GameOverOverlay;

public class Playing extends State implements Statemethods {
    private Bomber player;
    private BaldPirate enemy;
    private TileManager tileManager;
    private List<Bomb> bombs;
    private Game game;
    private GameOverOverlay gameOverOverlay;
    private boolean gameOver;

    public Playing(Game game) {
        super(game);
        this.game = game;
        initClasses();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    
    private void initClasses() {
        tileManager = new TileManager(game.getGamePanel());
        bombs = new ArrayList<>();
        player = new Bomber(90, 100, game.getGamePanel(), game);
        enemy = new BaldPirate(game.getGamePanel().screenWidth - 150, 90, game.getGamePanel(), game);

        player.setEnemy(enemy);
        enemy.setEnemy(player);

        this.gameOverOverlay = new GameOverOverlay(this);
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
    
    public void resetAll() {
		gameOver = false;
		player.reset();
		enemy.reset();
//		paused = false;
//		lvlCompleted = false;
//		playerDying = false;
//		player.resetAll();
//		enemyManager.resetAllEnemies();
//		objectManager.resetAllObjects();
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

    public TileManager getTileManager() {
        return tileManager;
    }

    @Override
    public void update() {
        if (!gameOver) {
            player.update();
            enemy.update();
            updateBombs();
            if ((!player.isAlive() && player.isDeathAnimationDone()) || 
                (!enemy.isAlive() && enemy.isDeathAnimationDone())) {
                gameOver = true;
            }
        } else {
            gameOverOverlay.update();
        }
    }

    @Override
    public void draw(Graphics g) {  
        tileManager.render(g);
        player.render(g);
        enemy.render(g);
        renderBombs(g);
        if (gameOver) {
        	gameOverOverlay.draw(g);
        }
    }
   

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                getPlayer().setUp(false);
                break;
            case KeyEvent.VK_A:
                getPlayer().setLeft(false);
                break;
            case KeyEvent.VK_D:
                getPlayer().setRight(false);
                break;
            case KeyEvent.VK_X:
                getPlayer().setAttackReady(true);
                break;
            case KeyEvent.VK_UP:
                getEnemy().setUp(false);
                break;
            case KeyEvent.VK_LEFT:
                getEnemy().setLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
                getEnemy().setRight(false);
                break;
            case KeyEvent.VK_SHIFT:
                getEnemy().setAttackReady(true);
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                getPlayer().jump();
                break;
            case KeyEvent.VK_A:
                getPlayer().setLeft(true);
                break;
            case KeyEvent.VK_D:
                getPlayer().setRight(true);
                break;
            case KeyEvent.VK_X:
                getPlayer().setAttacking(true);
                break;
            case KeyEvent.VK_UP:
                getEnemy().jump();
                break;
            case KeyEvent.VK_LEFT:
                getEnemy().setLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                getEnemy().setRight(true);
                break;
            case KeyEvent.VK_SHIFT:
                getEnemy().setAttacking(true);
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (gameOver) {
            gameOverOverlay.mouseMoved(e);
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if (gameOver) {
            gameOverOverlay.mousePressed(e);
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if (gameOver) {
            gameOverOverlay.mouseReleased(e);
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    	
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Bomber getPlayer() {
        return player;
    }

    public BaldPirate getEnemy() {
        return enemy;
    }

    private void resetGame() {
        player.reset();
        enemy.reset();
        bombs.clear();
    }
}
