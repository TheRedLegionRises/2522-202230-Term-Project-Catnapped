package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DisplayLevel {
    private Game game;
    private static final String terrainImagesURL = "/images/Terrain.png";
    private BufferedImage[] levelImages;
    private LevelInfo level1;

    public DisplayLevel(Game game) {
        this.game = game;
        loadAllSprites();
        level1 = new LevelInfo(LoadImages.GetLevelImages());
    }

    private void loadAllSprites() {
        BufferedImage img = LoadImages.GetImage(terrainImagesURL);
//        System.out.println("Image Height: " + img.getHeight() + " | Image Width: " + img.getWidth());
        levelImages = new BufferedImage[247];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++) {
                int currentIndex = i * 12 + j;
                levelImages[currentIndex] = img.getSubimage(j * 32, i * 32, 32, 32);
            }
        }
    }

    public void drawLevel(Graphics g) {

        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
                int index = level1.getSpriteIndex(i, j);
                g.drawImage(levelImages[index], i * 32, j * 32, 32, 32, null);
            }
        }
//        g.drawImage(levelImages[0], 10, 10, null);
//        g.drawImage(levelImages[1], 42, 10, null);
//        g.drawImage(levelImages[2], 74, 10, null);

    }

    public void update() {

    }
}
