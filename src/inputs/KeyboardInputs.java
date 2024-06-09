package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel;
import static utils.Constants.Directions.*;

public class KeyboardInputs implements KeyListener {

    private GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer().setUp(false);
                break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setLeft(false);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setRight(false);
                break;
            case KeyEvent.VK_X:
                gamePanel.getGame().getPlayer().setAttackReady(true);
            case KeyEvent.VK_UP:
                gamePanel.getGame().getEnemy().setUp(false);
                break;
            case KeyEvent.VK_LEFT:
                gamePanel.getGame().getEnemy().setLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
                gamePanel.getGame().getEnemy().setRight(false);
                break;
            case KeyEvent.VK_SHIFT:
                gamePanel.getGame().getEnemy().setAttackReady(true);
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer().jump();
                break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setLeft(true);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setRight(true);
                break;
            case KeyEvent.VK_X:
                gamePanel.getGame().getPlayer().setAttacking(true);
            case KeyEvent.VK_UP:
                gamePanel.getGame().getEnemy().jump();
                break;
            case KeyEvent.VK_LEFT:
                gamePanel.getGame().getEnemy().setLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                gamePanel.getGame().getEnemy().setRight(true);
                break;
            case KeyEvent.VK_SHIFT:
                gamePanel.getGame().getEnemy().setAttacking(true);
                break;
        }
    }

}
