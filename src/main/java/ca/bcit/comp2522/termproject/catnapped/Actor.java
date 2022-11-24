package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;

/**
 * Abstract Class Entity.
 * @author jerry
 * @version 2022
 */
public abstract class Actor {
    protected float x, y;
    protected final int currentHealth, maxHealth, height, width;
    private Rectangle playerHitbox;


    public Actor (float newXCoordinate, float newYCoordinate, int newMaxHealth, int newHeight, int newWidth) {
        this.x = newXCoordinate;
        this.y = newYCoordinate;
        this.maxHealth = newMaxHealth;
        this.currentHealth = newMaxHealth;
        this.height = newHeight;
        this.width = newWidth;

        playerHitbox = new Rectangle((int) x, (int) y, width, height);
    }

    public void updateHitbox() {
        //Empty white space to left because of the way sprite was created so we shift hitbox right
        playerHitbox.x = (int) x + 40;
        playerHitbox.y = (int) y;
    }

    //Could be protected
    public Rectangle getPlayerHitbox() {
        return playerHitbox;
    }

    public void drawPlayerHitbox(Graphics g) {
        g.setColor(Color.PINK);
        //Divided hitbox width by 3 to be more lenient on player (less raging)
        g.drawRect(playerHitbox.x, playerHitbox.y, playerHitbox.width / 3, playerHitbox.height);
    }

    public float getXCoordinate() {
        return this.x;
    }

    public float getYCoordinate() {
        return this.y;
    }
}
