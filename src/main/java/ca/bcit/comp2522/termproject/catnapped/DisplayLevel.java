package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DisplayLevel {
    private Game game;
    private static final String terrainImagesURL = "/images/Terrain.png";
    private BufferedImage[] levelImages;

    public DisplayLevel(Game game) {
        this.game = game;
        loadAllSprites();
    }

    private void loadAllSprites() {
        BufferedImage img = LoadImages.GetImage(terrainImagesURL);
//        System.out.println("Image Height: " + img.getHeight() + " | Image Width: " + img.getWidth());
        levelImages = new BufferedImage[247];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                int currentIndex = i * 8 + j;
                levelImages[currentIndex] = img.getSubimage(j * 32, i * 32, 32, 32);
            }
        }
    }

    public void drawLevel(Graphics g) {
        g.drawImage(levelImages[0], 10, 10, null);
        g.drawImage(levelImages[1], 42, 10, null);
        g.drawImage(levelImages[2], 74, 10, null);

    }

    public void update() {

    }
}
