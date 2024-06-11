package objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import main.Game;
import players.Player;
import tiles.Tile;
import static utils.Constants.PlayerConstants.*;

public class Bomb {
    private float x, y;
    private float velocityX, velocityY;
    private final float gravity = 0.1f;
    private boolean exploded, explosionCompleted;
    private BufferedImage[][] bombAnimations;
    private int animationTick, animationIndex;
    private final int animationSpeed = 10;
    private final Game game;
    private final int widthBombEffect = 100;
    private final int heightBombEffect = 100;

    private long explosionStartTime;
    private final long explosionDelay = 1000;

    private Rectangle solidArea;
    private boolean onGround;

    public Bomb(float x, float y, float velocityX, float velocityY, Game game, int toLeft) {
        this.x = x - (100 * toLeft);
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.exploded = false;
        this.explosionCompleted = false;
        this.game = game;
        this.onGround = false;

        this.solidArea = new Rectangle((int)x, (int)y, 64, 86); // Adjust size as needed

        loadAnimations();
        this.explosionStartTime = System.currentTimeMillis();
        System.out.println("Bomb created at: " + explosionStartTime);
    }

    private void loadAnimations() {
        String[] animationDir = { "1-Bomb Off", "2-Bomb On", "3-Explotion" };
        int[] bombAction = {BOMB_OFF, BOMB_ON, EXPLOTION};
        bombAnimations = new BufferedImage[3][];

        for (int i = 0; i < animationDir.length; i++) {
            bombAnimations[i] = new BufferedImage[GetSpriteAmount(bombAction[i])];
            for (int j = 0; j < GetSpriteAmount(bombAction[i]); j++) {
                String animationDirName = animationDir[i];
                BufferedImage animationImage = importImage(j + 1, animationDirName);
                if (animationImage == null) {
                    break;
                }
                bombAnimations[i][j] = animationImage;
            }
        }
    }

    private BufferedImage importImage(int frame, String dirName) {
        String path = String.format("/Objects-Item/BOMB/%s/%d.png", dirName, frame);

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

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - explosionStartTime > explosionDelay && !exploded) {
            exploded = true;
            animationIndex = 0;
            animationTick = 0;
        }
        updateAnimation();

        if (!exploded) {
            if (!onGround) {
                x += velocityX;
                y += velocityY;
                velocityY += gravity;

                // Update solid area position
                solidArea.setLocation((int)x, (int)y);

                if (checkCollision()) {
                    adjustPositionOnCollision();
                    onGround = true;
                    velocityX = 0;
                    velocityY = 0;
                }
            }
        }
    }

    private boolean checkCollision() {
        int tileSize = game.getTileManager().getTileSize();
        int leftTile = (int) (solidArea.x / tileSize);
        int rightTile = (int) ((solidArea.x + solidArea.width) / tileSize);
        int topTile = (int) (solidArea.y / tileSize);
        int bottomTile = (int) ((solidArea.y + solidArea.height) / tileSize);

        for (int col = leftTile; col <= rightTile; col++) {
            for (int row = topTile; row <= bottomTile; row++) {
                if (col >= 0 && col < game.getTileManager().getMapTileScheme().length &&
                    row >= 0 && row < game.getTileManager().getMapTileScheme()[0].length) {
                    Tile tile = game.getTileManager().getTile(col, row);
                    if (tile != null && tile.collision) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean collidesWith(Player player) {
        Rectangle bombRect = new Rectangle((int)x, (int)y, widthBombEffect, heightBombEffect); // Assuming you have width and height for bomb
        Rectangle playerRect = new Rectangle((int)player.getX(), (int)player.getY(), widthBombEffect, heightBombEffect);;
        return bombRect.intersects(playerRect);
    }
    
    public int getDamage() {
        return 50; // Example damage value
    }

    private void adjustPositionOnCollision() {
        int tileSize = game.getTileManager().getTileSize();
        int bottomRow = (int) ((y + solidArea.height) / tileSize);
        y = bottomRow * tileSize - solidArea.height;
        solidArea.setLocation((int)x, (int)y);
    }

    private void updateAnimation() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (exploded) {
                if (animationIndex >= bombAnimations[2].length) {
                    explosionCompleted = true;
                    return;
                }
            } else {
                if (animationIndex >= bombAnimations[1].length) {
                    animationIndex = 0;
                }
            }
        }
    }

    public boolean hasExploded() {
        return exploded && explosionCompleted;
    }

    public void render(Graphics g) {
        int currentAnimation = exploded ? 2 : 1;
        BufferedImage currentFrame = bombAnimations[currentAnimation][Math.min(animationIndex, bombAnimations[currentAnimation].length - 1)];
        g.drawImage(currentFrame, (int) x, (int) y, null);
    }
}
