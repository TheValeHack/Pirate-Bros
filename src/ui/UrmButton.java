// In ui/UrmButton.java
package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class UrmButton {
    private int x, y, width, height, id;
    private BufferedImage[] imgs;
    private boolean mouseOver, mousePressed;

    public UrmButton(int x, int y, int width, int height, int id, BufferedImage[] imgs) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.imgs = imgs;
    }

    public void draw(Graphics g) {
        g.drawImage(imgs[id * 3 + (mousePressed ? 2 : mouseOver ? 1 : 0)], x, y, width, height, null);
    }

    public void update() {
        // No updates required for now
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void resetBools() {
        this.mouseOver = false;
        this.mousePressed = false;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
