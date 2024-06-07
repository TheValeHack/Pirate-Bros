package players;

import java.awt.Rectangle;

import main.GamePanel;

public abstract class Player {
	protected float x;
	protected float y;
	protected Rectangle solidArea;
	protected GamePanel gamePanel; 
	
	
	public Player(float x, float y, GamePanel gamePanel) {
		this.x = x;
		this.y = y;
		this.gamePanel = gamePanel;
	}
}
