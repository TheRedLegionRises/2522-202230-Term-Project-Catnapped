package ca.bcit.comp2522.termproject.catnapped;

/**
 * Abstract Class Entity.
 * @author jerry
 * @version 2022
 */
public abstract class Entity {
    private final float x, y;

    public Entity (float newXCoordinate, float newYCoordinate) {
        this.x = newXCoordinate;
        this.y = newYCoordinate;
    }

    public float getXCoordinate() {
        return this.x;
    }

    public float getYCoordinate() {
        return this.y;
    }
}
