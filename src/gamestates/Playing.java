package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import players.BaldPirate;
import players.BigGuy;
import players.Bomber;
import players.Captain;
import players.Cucumber;
import players.Whale;
import tiles.TileManager;
import main.Game;
import objects.Bomb;

public class Playing extends State implements Statemethods {
	private Bomber player;
//  private final Captain enemy;
//  private final BigGuy enemy;
	private BaldPirate enemy;
//  private final Cucumber enemy;
//  private final Whale enemy;
	private TileManager tileManager;
	private List<Bomb> bombs;
	private Game game;

	public Playing(Game game) {
		super(game);
		this.game = game;
		initClasses();
	}

	private void initClasses() {
		tileManager = new TileManager(game.getGamePanel());
		bombs = new ArrayList<>();	
        player = new Bomber(90, 100, game.getGamePanel(), game);
//        enemy = new Captain(gamePanel.screenWidth - 150, 200, gamePanel, this);
//        enemy = new BigGuy(gamePanel.screenWidth - 150, 200, gamePanel, this);
        enemy = new BaldPirate(game.getGamePanel().screenWidth - 150, 100, game.getGamePanel(), game);
//        enemy = new Cucumber(gamePanel.screenWidth - 150, 200, gamePanel, this);
//        enemy = new Whale(gamePanel.screenWidth - 150, 200, gamePanel, this);
        
        player.setEnemy(enemy);
        enemy.setEnemy(player);

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
    public TileManager getTileManager() {
        return tileManager;
    }

	@Override
	public void update() {
		player.update();
		enemy.update();
		updateBombs();
	}

	@Override
	public void draw(Graphics g) {
		tileManager.render(g);
		player.render(g);
		enemy.render(g);
		renderBombs(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
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
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

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

}