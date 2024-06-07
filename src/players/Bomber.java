package players;

import static utils.Constants.BomberConstants.*;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

import main.GamePanel;
import tiles.Tile;

public class Bomber extends Player {
    private BufferedImage[][] animations;
    private int animationTick, animationIndex;
    private final int animationSpeed = 12;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down;
    private final float playerSpeed = 2.0f;
    private boolean attackReady = true;


    public Bomber(float x, float y, GamePanel gamePanel) {
        super(x, y, gamePanel);
        solidArea = new Rectangle(8, 20, 65, 72);
        
        loadAnimations();
    }

    public boolean checkCollision(int nextX, int nextY) {
        Rectangle futureSolidArea = new Rectangle(nextX + solidArea.x, nextY + solidArea.y, solidArea.width, solidArea.height);
        
        for (int col = 0; col < gamePanel.maxScreenCol; col++) {
            for (int row = 0; row < gamePanel.maxScreenRow; row++) {
                Tile tile = gamePanel.getGame().getTileManager().getTile(col, row);
                if (tile.collision) {
                    int tileX = col * gamePanel.tileSize;
                    int tileY = row * gamePanel.tileSize;
                    Rectangle tileRect = new Rectangle(tileX, tileY, gamePanel.tileSize, gamePanel.tileSize);
                    if (futureSolidArea.intersects(tileRect)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    
    public void update() {
        updatePos();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][animationIndex], (int) x, (int) y, 96, 96, null);
    }

    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetSpriteAmount(playerAction)) {
                animationIndex = 0;
                attacking = false;
            }
        }
    }

    private void setAnimation() {
        int startAnimation = playerAction;

        if (attacking) {
            playerAction = HIT;
        } else if (moving) {
            playerAction = RUN;
        } else {
            playerAction = IDLE;
        }

        if (startAnimation != playerAction) {
            resetAnimationTick();
        }
    }

    private void resetAnimationTick() {
        animationTick = 0;
        animationIndex = 0;
    }

    private void updatePos() {
        moving = false;

        if (left && !right && !checkCollision((int)(x - playerSpeed), (int)y)) {
            x -= playerSpeed;
            moving = true;
        } else if (right && !left && !checkCollision((int)(x + playerSpeed), (int)y)) {
            x += playerSpeed;
            moving = true;
        }

        if (up && !down && !checkCollision((int)x, (int)(y - playerSpeed))) {
            y -= playerSpeed;
            moving = true;
        } else if (down && !up && !checkCollision((int)x, (int)(y + playerSpeed))) {
            y += playerSpeed;
            moving = true;
        }
    }


    private void loadAnimations() {
		String[] animationDir = ANIMATION_DIR;
		animations = new BufferedImage[11][26];
		
		for(int i = 0; i < animationDir.length; i++) {
			for(int j = 0; j < GetSpriteAmount(playerAction); j++) {
				String animationDirName = animationDir[i];
				BufferedImage animationImage = importImage(j+1, animationDirName);
				if(animationImage == null) {
					break;
				}
				animations[i][j] = animationImage;
			}
		}
		
	}
    
    private BufferedImage importImage(int frame, String dirName) {
	    String path = String.format("/Player-Bomb Guy/%s/%d.png", dirName, frame);
	    
	    try (InputStream is = getClass().getResourceAsStream(path)) {
	        if (is == null) {
	            return null;
	        }
	        return ImageIO.read(is);
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }
    public boolean getAttacking() {
    	return attacking;
    }
    public boolean getAttackReady() {
    	return attackReady;
    }
    public void setAttacking(boolean attacking) {
    	if (attackReady && attacking) {
            this.attacking = true;
            attackReady = false;
        }
    }
    public void setAttackReady(boolean attackReady) {
        this.attackReady = attackReady;
        if (attackReady == true) {
            this.attacking = false; 
        }
    }
    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
}
