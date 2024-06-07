package players;

import java.awt.Rectangle;

public abstract class Player {
	protected float x;
	protected float y;
	protected Rectangle solidArea;
	public boolean collisionOn = false; 
	
	
	public Player(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
