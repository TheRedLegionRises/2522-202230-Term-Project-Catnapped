package ca.bcit.comp2522.termproject.catnapped;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static ca.bcit.comp2522.termproject.catnapped.HelperMethods.GetLevelData;
import static ca.bcit.comp2522.termproject.catnapped.HelperMethods.GetEnemies;

public class LevelInfo {
    private BufferedImage img;
    private ArrayList<Enemy> enemys;

    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private int[][] levelImage;


    public LevelInfo(BufferedImage img) {
        this.img = img;
        createLevelData();
        createEnemies();
        calcLvlOffsets();
    }
    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TEST_SIZE * maxTilesOffset;
    }

    private void createLevelData() {
        levelImage = GetLevelData(img);
    }

    private void createEnemies() {
        enemys = GetEnemies(img);
    }
    public int getSpriteIndex(int x, int y) {
        return levelImage[y][x];
    }

    public int[][] getLevelImage() {
        return levelImage;
    }

    public int getLvlOffset(){
        return maxLvlOffsetX;
    }
    public ArrayList<Enemy> getEnemys() {
        return enemys;
    }

}
