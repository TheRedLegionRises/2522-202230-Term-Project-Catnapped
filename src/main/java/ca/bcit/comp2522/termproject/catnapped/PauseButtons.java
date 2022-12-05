package ca.bcit.comp2522.termproject.catnapped;

import java.awt.Rectangle;

/**
 * PauseButtons Class. To give/show pause buttons functionality.
 * @author Jerry and Bryan
 * @version 2022
 */
public class PauseButtons {
    protected Rectangle boundaries;
    protected int x, y, width, height;

    public PauseButtons(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        createBoundaries();
    }

    private void createBoundaries() {
        boundaries = new Rectangle(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Rectangle getBoundaries() {
        return boundaries;
    }

    public void setBoundaries(Rectangle boundaries) {
        this.boundaries = boundaries;
    }

}
