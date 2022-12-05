package ca.bcit.comp2522.termproject.catnapped;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static ca.bcit.comp2522.termproject.catnapped.Constants.MenuUI.UtilButtons.UTIL_BUTTON_SIZE;

/**
 * UtilButtons class. Utility buttons for muting, restarting levels, back to home screen.
 * @author Jerry and Bryan
 * @version 2022
 */
public class UtilButtons extends PauseButtons {

    private int rowIndex, index;
    private BufferedImage[] imgs;
    private boolean mouseOver, mousePressed;

    public UtilButtons(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        loadImgs();
    }

    private void loadImgs() {
        BufferedImage temp = LoadImages.GetImage(LoadImages.UTIL_BUTTONS);
        imgs = new BufferedImage[3];
        for (int i = 0; i < imgs.length; i++)
            imgs[i] = temp.getSubimage(i * UTIL_BUTTON_SIZE, rowIndex * UTIL_BUTTON_SIZE, UTIL_BUTTON_SIZE, UTIL_BUTTON_SIZE);

    }

    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;

    }

    public void draw(Graphics g) {
        g.drawImage(imgs[index], x, y, UTIL_BUTTON_SIZE, UTIL_BUTTON_SIZE, null);
    }

    public void resetBooleans() {
        mouseOver = false;
        mousePressed = false;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

}
