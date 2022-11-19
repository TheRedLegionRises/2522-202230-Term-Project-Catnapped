package ca.bcit.comp2522.termproject.catnapped;

/**
 * Abstract Class Entity.
 * @author jerry
 * @version 2022
 */
public abstract class Entity {
    private final float x, y;
    private final int currentHealth, maxHealth, height, width;


    public Entity (float newXCoordinate, float newYCoordinate, int newMaxHealth, int newHeight, int newWidth) {
        this.x = newXCoordinate;
        this.y = newYCoordinate;
        this.maxHealth = newMaxHealth;
        this.currentHealth = newMaxHealth;
        this.height = newHeight;
        this.width = newWidth;
    }

    public float getXCoordinate() {
        return this.x;
    }

    public float getYCoordinate() {
        return this.y;
    }
}
