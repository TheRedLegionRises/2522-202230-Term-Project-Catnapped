package ca.bcit.comp2522.termproject.catnapped;

/**
 * A LevelInfo Class. Contains all the information about a level.
 * @author jerry and bryan
 * @version 2022
 */
public class LevelInfo {
    private int[][] levelImage;

    /**
     * Constructor for our LevelInfo class
     * @param arrayOfLevelImages a 2D integer array
     */
    public LevelInfo(int[][] arrayOfLevelImages) {
        this.levelImage = arrayOfLevelImages;
    }

    /**
     * Returns the sprite stored in a specific position
     * @param x an integer
     * @param y an integer
     * @return an integer
     */
    public int getSpriteIndex(int x, int y) {
        return levelImage[y][x];
    }

    /**
     * Returns the 2D integer array levelImage
     * @return a 2D integer array
     */
    public int[][] getLevelImage() {
        return levelImage;
    }

}
