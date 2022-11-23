package ca.bcit.comp2522.termproject.catnapped;

public class LevelInfo {
    private int[][] levelImage;

    public LevelInfo(int[][] arrayOfLevelImages) {
        this.levelImage = arrayOfLevelImages;
    }

    public int getSpriteIndex(int x, int y) {
        return levelImage[y][x];
    }

}
