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
    protected final int currentHealth, maxHealth, height, width;
    protected Rectangle2D.Float playerHitbox;


    public Actor (float newXCoordinate, float newYCoordinate, int newMaxHealth, int newHeight, int newWidth) {
        this.x = newXCoordinate;
        this.y = newYCoordinate;
        this.maxHealth = newMaxHealth;
        this.currentHealth = newMaxHealth;
        this.height = newHeight;
        this.width = newWidth;
    }

//    public void updateHitbox() {
//        //Empty white space to left because of the way sprite was created so we shift hitbox right
//        playerHitbox.x = (int) x + 40;
//        playerHitbox.y = (int) y;
//    }

    protected void createHitbox(float xCoordinate, float yCoordinate, float width, float height) {
        playerHitbox = new Rectangle2D.Float( xCoordinate, yCoordinate, width, height);

    }

    //Could be protected
    public Rectangle2D.Float getHitbox() {
        return playerHitbox;
    }

    public void drawPlayerHitbox(Graphics g) {
        g.setColor(Color.PINK);
        //Divided hitbox width by 3 to be more lenient on player (less raging)
        g.drawRect((int) playerHitbox.x, (int) playerHitbox.y, (int) playerHitbox.width, (int) playerHitbox.height);
    }

    public float getXCoordinate() {
        return this.x;
    }

    public float getYCoordinate() {
        return this.y;
    }
}
