package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;
import ui.MenuButton;

public class Menu extends State implements Statemethods {
	
	private MenuButton[] buttons = new MenuButton[2];
	private BufferedImage backgroundImg, islandImg;
	private int menuX, menuY, menuWidth, menuHeight;
	private static final String MENU_BACKGROUND = "menu_background.png";
	private static final String ISLAND_BACKGROUND = "island_background.jpg";
	private int GAME_WIDTH = 1280;
	private double SCALE = 1.5;

	public Menu(Game game) {
		super(game);
		loadButtons();
		loadBackground();
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
	
	private void loadBackground() {
		islandImg = GetSpriteAtlas(ISLAND_BACKGROUND);
		backgroundImg = GetSpriteAtlas(MENU_BACKGROUND);
		menuWidth = (int) (backgroundImg.getWidth() * SCALE);
		menuHeight = (int) (backgroundImg.getHeight() * SCALE);
		menuX = GAME_WIDTH / 2 - menuWidth / 2;
		menuY = (int) (45 * SCALE);

	}

	private void loadButtons() {
		buttons[0] = new MenuButton(GAME_WIDTH / 2, (int) (150 * SCALE), 0, Gamestate.PLAYING);
		buttons[1] = new MenuButton(GAME_WIDTH / 2, (int) (220 * SCALE), 2, Gamestate.QUIT);
	}

	@Override
	public void update() {
		for (MenuButton mb : buttons) {
			mb.update();
		}
			
	}

	@Override
	public void draw(Graphics g) {
		int width = 1280;
	    int height = 768;
	    
	    g.drawImage(islandImg, 0, 0, width, height, null);

		g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);

		for (MenuButton mb : buttons) {
			mb.draw(g);
		}
	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				mb.setMousePressed(true);
			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				if (mb.isMousePressed())
					mb.applyGamestate();
				break;
			}
		}

		resetButtons();

	}

	private void resetButtons() {
		for (MenuButton mb : buttons)
			mb.resetBools();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for (MenuButton mb : buttons) {
			mb.setMouseOver(false);
		}
			

		for (MenuButton mb : buttons)
			if (isIn(e, mb)) {
				mb.setMouseOver(true);
				break;
			}

	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			Gamestate.state = Gamestate.PLAYING;

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}