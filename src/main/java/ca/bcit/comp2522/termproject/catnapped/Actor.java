package ca.bcit.comp2522.termproject.catnapped;

import java.awt.geom.Rectangle2D;

/**
 * Abstract Class Entity.
 * @author jerry and bryan
 * @version 2022
 */
public abstract class Actor {
    protected float x, y;
    protected final int height, width;
    protected Rectangle2D.Float actorHitbox;

    /**
     * Constructor for the actor abstract class
     * @param newXCoordinate a float
     * @param newYCoordinate a float
     * @param newHeight an integer
     * @param newWidth an integer
     */
    public Actor (float newXCoordinate, float newYCoordinate, int newHeight, int newWidth) {
        this.x = newXCoordinate;
        this.y = newYCoordinate;
        this.height = newHeight;
        this.width = newWidth;
    }

    /**
     * Creates the actor's hitbox
     * @param xCoordinate a float
     * @param yCoordinate a float
     * @param width an integer
     * @param height an integer
     */
    protected void createHitbox(float xCoordinate, float yCoordinate, float width, float height) {
        actorHitbox = new Rectangle2D.Float( xCoordinate, yCoordinate, width, height);

    }

    /**
     * Returns the actor's hitbox
     * @return a Rectangle2D.Float object
     */
    public Rectangle2D.Float getHitbox() {
        return actorHitbox;
    }
}
