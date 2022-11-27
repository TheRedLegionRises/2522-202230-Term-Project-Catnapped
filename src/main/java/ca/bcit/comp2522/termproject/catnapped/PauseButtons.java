package ca.bcit.comp2522.termproject.catnapped;

import java.awt.Rectangle;

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

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rectangle getBoundaries() {
        return boundaries;
    }

    public void setBoundaries(Rectangle boundaries) {
        this.boundaries = boundaries;
    }

}