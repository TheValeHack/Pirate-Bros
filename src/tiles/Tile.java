package tiles;

import java.awt.image.BufferedImage;

public class Tile {
	public BufferedImage image;
	public boolean collision;
	
	public Tile() {
		this.collision = true;
	}
	public Tile(boolean collision) {
		this.collision = collision;
	}
}
