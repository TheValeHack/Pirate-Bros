package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import gamestates.Gamestate;
import gamestates.Playing;

public class GameOverOverlay {
    private Playing playing;
    private BufferedImage img;
    private BufferedImage[][] buttons;
    private double imgW, imgH;
    private int imgX, imgY;
    private UrmButton restart, quit;

    public GameOverOverlay(Playing playing) {
        this.playing = playing;
        loadAssets();
        createButtons();
    }

    private BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;
        try (InputStream is = getClass().getResourceAsStream("/ui-sprites/" + fileName)) {
            if (is == null) {
                throw new IOException("Resource not found: " + fileName);
            }
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
    
    private void loadAssets() {
        img = GetSpriteAtlas("death_screen.png");
        BufferedImage buttonsSprite = GetSpriteAtlas("urm_buttons.png");
        buttons = new BufferedImage[2][6];
        for (int i = 0; i < 6; i++) {
            buttons[0][i] = buttonsSprite.getSubimage((i % 3) * 56, 56, 56, 56);
        }
        for (int i = 0; i < 6; i++) {
            buttons[1][i] = buttonsSprite.getSubimage((i % 3) * 56, 112, 56, 56);
        }

        imgW = img.getWidth() * 1.5;
        imgH = img.getHeight() * 1.5;
        imgX = 1280 / 2 - (int)imgW / 2;
        imgY = 768 / 2 - (int)imgH / 2;
    }

    private void createButtons() {
        int restartX = imgX + 64;
        int quitX = imgX + (int)imgW - 128;
        int y = imgY + (int)imgH - 168;

        restart = new UrmButton(quitX, y, 56, 56, 0, buttons[0]);
        quit = new UrmButton(restartX, y, 56, 56, 1, buttons[1]);
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 0, 1280, 768);

        g.drawImage(img, imgX, imgY, (int)imgW, (int)imgH, null);

        restart.draw(g);
        quit.draw(g);
    }

    public void update() {
        restart.update();
        quit.update();
    }

    public void mouseMoved(MouseEvent e) {
        restart.setMouseOver(false);
        quit.setMouseOver(false);

        if (restart.getBounds().contains(e.getX(), e.getY()))
            restart.setMouseOver(true);
        else if (quit.getBounds().contains(e.getX(), e.getY()))
            quit.setMouseOver(true);
    }

    public void mousePressed(MouseEvent e) {
        if (restart.getBounds().contains(e.getX(), e.getY()))
            restart.setMousePressed(true);
        else if (quit.getBounds().contains(e.getX(), e.getY()))
            quit.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (restart.getBounds().contains(e.getX(), e.getY())) {
            if (restart.isMousePressed()) {
                playing.resetAll();
                Gamestate.state = Gamestate.PLAYING;
            }
        } else if (quit.getBounds().contains(e.getX(), e.getY())) {
            if (quit.isMousePressed()) {
            	playing.resetAll();
                Gamestate.state = Gamestate.MENU;
            }
        }

        restart.resetBools();
        quit.resetBools();
    }
}
