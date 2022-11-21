package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DisplayLevel {
    private Game game;
    private final String levelURL = "/images/Terrain.png";
    private BufferedImage[] levelImages;
    private BufferedImage levelImage;

    public DisplayLevel(Game game) {
        this.game = game;
//        levelImage = LoadImages.GetImage(levelURL);
        loadAllSprites();
    }

    private void loadAllSprites() {
        BufferedImage img = LoadImages.GetImage(levelURL);
//        System.out.println("Image Height: " + img.getHeight() + " | Image Width: " + img.getWidth());
        levelImages = new BufferedImage[247];

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 19; j++) {
                int currentIndex = i * 19 + j;
                levelImages[currentIndex] = img.getSubimage(j * 32, i * 32, 32, 32);
            }
        }
    }

    //Has Blank Spaces - 1 sprite worth along the outside
    public void drawLevel(Graphics g) {
        g.drawImage(levelImages[20], 10, 10, null);
    }

    public void update() {

    }
}
