package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;
import java.awt.image.BufferedImage;

import static ca.bcit.comp2522.termproject.catnapped.LoadImages.TERRAIN_IMG;

public class DisplayLevel {
    private Game game;
    private BufferedImage[] levelImages;
    private LevelInfo level1;

    public DisplayLevel(Game game) {
        this.game = game;
        loadAllSprites();
        level1 = new LevelInfo(LoadImages.GetLevelImages());
    }

    private void loadAllSprites() {
        BufferedImage img = LoadImages.GetImage(TERRAIN_IMG);
//       System.out.println("Image Height: " + img.getHeight() + " | Image Width: " + img.getWidth());
        levelImages = new BufferedImage[48];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++) {
                int currentIndex = i * 12 + j;
                levelImages[currentIndex] = img.getSubimage(j * 32, i * 32, 32, 32);
            }
        }
    }

    public void drawLevel(Graphics g, int xLvlOffset) {

        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < level1.getLevelImage()[0].length; i++) {
                int index = level1.getSpriteIndex(i, j);
                g.drawImage(levelImages[index], i * 32 - xLvlOffset, j * 32, 32, 32, null);
            }
        }
//        g.drawImage(levelImages[0], 10, 10, null);
//        g.drawImage(levelImages[1], 42, 10, null);
//        g.drawImage(levelImages[2], 74, 10, null);

    }

    public void update() {

    }

    public LevelInfo getCurrentLevel() {
        return level1;
    }
}