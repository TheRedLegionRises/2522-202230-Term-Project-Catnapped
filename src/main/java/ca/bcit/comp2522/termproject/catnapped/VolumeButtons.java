package ca.bcit.comp2522.termproject.catnapped;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static ca.bcit.comp2522.termproject.catnapped.Constants.MenuUI.VolumeButtons.*;

/**
 * VolumeButtons class. Volume buttons to adjust volume.
 * @author Jerry and Bryan
 * @version 2022
 */
public class VolumeButtons extends PauseButtons {
    private int index = 0;
    private BufferedImage slider;
    private BufferedImage[] imgs;
    private int buttonX, minX, maxX;
    private boolean mouseOver, mousePressed;
    /**
     * VolumeButtons constructor
     @param x position for volume buttons
     @param y position for volume buttons
     @param width for volume buttons
     @param height for volume buttons
     */
    public VolumeButtons(int x, int y, int width, int height) {
        super(x + width / 2, y, VOLUME_BUTTON_WIDTH, height);
        boundaries.x -= VOLUME_BUTTON_WIDTH / 2;
        buttonX = x + width / 2;
        this.x = x;
        this.width = width;
        minX = x + VOLUME_BUTTON_WIDTH / 2;
        maxX = x + width - VOLUME_BUTTON_WIDTH / 2;
        loadImgs();
    }
    /**
     * Load volume buttons for pause menu
     */
    private void loadImgs() {
        BufferedImage temp = LoadImages.GetImage(LoadImages.VOLUME_BUTTONS);
        imgs = new BufferedImage[3];
        for (int i = 0; i < imgs.length; i++)
            imgs[i] = temp.getSubimage(i * VOLUME_BUTTON_WIDTH, 0, VOLUME_BUTTON_WIDTH, VOLUME_BUTTON_HEIGHT);

        slider = temp.getSubimage(3 * VOLUME_BUTTON_WIDTH, 0, SLIDER_WIDTH, VOLUME_BUTTON_HEIGHT);

    }

    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
    }

    public void draw(Graphics g) {
        g.drawImage(slider, x, y, width, height, null);
        g.drawImage(imgs[index], buttonX - VOLUME_BUTTON_WIDTH / 2, y, VOLUME_BUTTON_WIDTH, height, null);

    }

    public void changeX(int x) {
        if (x < minX)
            buttonX = minX;
        else if (x > maxX)
            buttonX = maxX;
        else
            buttonX = x;

        boundaries.x = buttonX - VOLUME_BUTTON_WIDTH / 2;

    }

    public void resetBooleans() {
        mouseOver = false;
        mousePressed = false;
    }

//    public boolean isMouseOver() {
//        return mouseOver;
//    }

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