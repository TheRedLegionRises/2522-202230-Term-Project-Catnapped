package ca.bcit.comp2522.termproject.catnapped;

/**
 * A LevelInfo Class. Contains all the information about a level.
 * @author Jerry and Bryan
 * @version 2022
 */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static ca.bcit.comp2522.termproject.catnapped.HelperMethods.*;

public class LevelInfo {
    private BufferedImage img;
    private ArrayList<Enemy> enemys;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private int[][] levelImage;
    private Point PlayerSpawn;

    /**
     * Constructor for our LevelInfo class
     * @param arrayOfLevelImages a 2D integer array
     */
//    public LevelInfo(int[][] arrayOfLevelImages) {
//        this.levelImage = arrayOfLevelImages;
//    }

    /**
     * Constructor for our LevelInfo class
     * @param img a 2D integer array
     */
    public LevelInfo(BufferedImage img) {
        this.img = img;
        createLevelData();
        createEnemies();
        calcLvlOffsets();
        calcPlayerSpawn();
    }
    /**
     * Calculates the player spawn from img
     */
    private void calcPlayerSpawn() {
        PlayerSpawn = GetPlayerSpawn(img);
    }

    /**
     * Calculates the offset for scrolling - WOIP
     */
    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TEST_SIZE * maxTilesOffset;
    }

    /**
     * Gets level data
     */
    private void createLevelData() {
        levelImage = GetLevelData(img);
    }
    /**
     * Renders enemies for levels
     */
    private void createEnemies() {
        enemys = GetEnemies(img);
    }
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

    public int getLvlOffset(){
        return maxLvlOffsetX;
    }
    public ArrayList<Enemy> getEnemys() {
        return enemys;
    }

    public Point getPlayerSpawn() {
        return PlayerSpawn;
    }

}
