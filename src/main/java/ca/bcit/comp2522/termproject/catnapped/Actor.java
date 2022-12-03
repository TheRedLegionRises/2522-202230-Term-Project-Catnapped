package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Abstract Class Entity.
 * @author jerry
 * @version 2022
 */
public abstract class Actor {
    protected float x, y;
    protected final int height, width;
    protected Rectangle2D.Float actorHitbox;

    public Actor (float newXCoordinate, float newYCoordinate, int newHeight, int newWidth) {
        this.x = newXCoordinate;
        this.y = newYCoordinate;
        this.height = newHeight;
        this.width = newWidth;
    }

//    public void updateHitbox() {
//        //Empty white space to left because of the way sprite was created so we shift hitbox right
//        playerHitbox.x = (int) x + 40;
//        playerHitbox.y = (int) y;
//    }

    protected void createHitbox(float xCoordinate, float yCoordinate, float width, float height) {
        actorHitbox = new Rectangle2D.Float( xCoordinate, yCoordinate, width, height);

    }

    //Could be protected
    public Rectangle2D.Float getHitbox() {
        return actorHitbox;
    }

    public void drawActorHitbox(Graphics g, int xoffset) {
        g.setColor(Color.PINK);
        g.drawRect((int) actorHitbox.x - xoffset, (int) actorHitbox.y, (int) actorHitbox.width, (int) actorHitbox.height);
    }

    public float getXCoordinate() {
        return this.x;
    }

    public float getYCoordinate() {
        return this.y;
    }
}
