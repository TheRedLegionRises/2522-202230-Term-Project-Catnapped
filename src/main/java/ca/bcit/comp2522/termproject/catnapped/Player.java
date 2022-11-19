package ca.bcit.comp2522.termproject.catnapped;

/**
 * Player Class.
 * @author jerry
 * @version 2022
 */
public class Player extends Entity{

    private final String imageURL = "imageURLGoesHere";
    public Player(float newXCoordinate, float newYCoordinate, int newMaxHealth, int newHeight, int newWidth) {
        super(newXCoordinate, newYCoordinate, newMaxHealth, newHeight, newWidth);
    }

    public void updatePosition() {

    }

    public void renderPlayer() {

    }

    public String getImageLocation() {
        return this.imageURL;
    }
}
