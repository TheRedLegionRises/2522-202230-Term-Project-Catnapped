package ca.bcit.comp2522.termproject.catnapped;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static ca.bcit.comp2522.termproject.catnapped.Constants.MenuUI.Buttons.*;

/**
 * MenuButtons Class. To give buttons function.
 * @author Jerry and Bryan
 * @version 2022
 */
public class MenuButtons {
    private Gamestate state;
    private Rectangle boundaries;
    private BufferedImage[] imgs;
    private boolean mouseOver, mousePressed;
    private int xOffsetCenter = BUTTON_WIDTH / 2;
    private int xPosition, yPosition, rowIndex, order;
    /**
     * MenuButtons constructor
     @param xPos x position of Menu buttons
     @param yPos y position of Menu buttons
     @param rowIndex index of which button
     @param state current state of game
     */
    public MenuButtons(int xPos, int yPos, int rowIndex, Gamestate state) {
        this.xPosition = xPos;
        this.yPosition = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImgs();
        clickableArea();
    }

    public void update() {
        order = 0;
        if (mouseOver)
            order = 1;
        if (mousePressed)
            order = 2;
    }

    public void draw(Graphics g) {
        g.drawImage(imgs[order], xPosition - xOffsetCenter, yPosition, BUTTON_WIDTH, BUTTON_HEIGHT, null);
    }

    private void clickableArea() {
        boundaries = new Rectangle(xPosition - xOffsetCenter, yPosition, BUTTON_WIDTH, BUTTON_HEIGHT);

    }
    /**
     * Loads buttons of menu
     */
    private void loadImgs() {
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadImages.GetImage(LoadImages.MENU_BUTTONS);
        for (int i = 0; i < imgs.length; i++)
            imgs[i] = temp.getSubimage(i * BUTTON_WIDTH, rowIndex * BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
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

    public Rectangle getBoundaries() {
        return boundaries;
    }

    public void applyGamestate() {
        Gamestate.state = state;
    }

    public void resetBooleans() {
        mousePressed = false;
        mouseOver = false;
    }

}