package players;

import static utils.Constants.EnemyConstants.*;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

import main.Game;
import main.GamePanel;
import objects.Bomb;
import tiles.Tile;

public class Cucumber extends Player {
    private BufferedImage[][] animations;
    private int animationTick, animationIndex;
    private final int animationSpeed = 12;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down;
    private final float playerSpeed = 2.0f;
    private boolean attackReady = true;
    private boolean isJumping = false;
    private boolean isFalling = false;
    private float jumpSpeed = 10.0f;
    private float fallSpeed = 0;
    private final float gravity = 0.065f;
    private final float maxFallSpeed = 5.0f;
    private Game game;
    
    private Bomb activeBomb;

    public Cucumber(float x, float y, GamePanel gamePanel, Game game) {
        super(x, y, 100, gamePanel);
        this.game = game;
        solidArea = new Rectangle(8, 20, 65, 74);
        
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
        if (activeBomb != null) {
            activeBomb.update();
            if (activeBomb.hasExploded()) {
                activeBomb = null;
            }
        }
    }

    public void render(Graphics g) {
    	int faceLeft = facingLeft ? -1 : 1;
    	int widthLeft = facingLeft ? 96 : 0;
        g.drawImage(animations[playerAction][animationIndex], (int) x + widthLeft, (int) y, faceLeft * 96, 96, null);
        if (activeBomb != null) {
            activeBomb.render(g);
        }
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

        if(isAlive()) {
        	if (attacking) {
                playerAction = HIT;
                if (activeBomb == null) {
                    throwBomb();
                }
            } else if (isJumping) {
                playerAction = JUMP;
            } else if (isFalling) {
                playerAction = FALL;
            } else if (moving) {
                playerAction = RUN;
            } else {
                playerAction = IDLE;
            }

        } else {
        	playerAction = DEAD_HIT;
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

        if(isAlive()) {
        	if (left && !right && !checkCollision((int)(x - playerSpeed), (int)y)) {
                x -= playerSpeed;
                moving = true;
                facingLeft = true;
            } else if (right && !left && !checkCollision((int)(x + playerSpeed), (int)y)) {
                x += playerSpeed;
                moving = true;
                facingLeft = false;
            }

            if (isJumping) {
                if (!checkCollision((int)x, (int)(y - jumpSpeed))) {
                    y -= jumpSpeed;
                    jumpSpeed -= gravity;
                    if (jumpSpeed <= 0) {
                        isJumping = false;
                        isFalling = true;
                        fallSpeed = 0;
                    }
                } else {
                    isJumping = false;
                    isFalling = true;
                    fallSpeed = 0;
                }
            } else if (isFalling || !checkCollision((int)x, (int)(y + 1))) {
                isFalling = true;
                if (!checkCollision((int)x, (int)(y + fallSpeed))) {
                    y += fallSpeed;
                    fallSpeed += gravity;
                    if (fallSpeed > maxFallSpeed) {
                        fallSpeed = maxFallSpeed;
                    }
                } else {
                    isFalling = false;
                    fallSpeed = 0;
                }
            } else {
                fallSpeed = 0;
            }
        }
    }
    
    private void throwBomb() {
        if (activeBomb == null) {
        	float bombInitialVelocityX = 1.5f; // Adjust as needed
            float bombInitialVelocityY = -3.5f; // Adjust as needed
            int toLeft = facingLeft ? -1 : 1;
            activeBomb = new Bomb(x + 160 * toLeft, y, bombInitialVelocityX * toLeft, bombInitialVelocityY, game, toLeft);
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
        String path = String.format("/Enemy-Cucumber/%s/%d.png", dirName, frame);
        
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

    public void jump() {
        if (!isJumping && !isFalling) {
            isJumping = true;
            jumpSpeed = 5.0f;
        }
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
