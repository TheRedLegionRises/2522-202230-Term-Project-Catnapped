package ca.bcit.comp2522.termproject.catnapped;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadImages {
    private static final String terrainImagesURL = "/images/Terrain.png";
    private static final String testLevel = "/images/level_one_data.png";

    public static BufferedImage GetImage(String filePath) {
        BufferedImage img = null;
        InputStream is = LoadImages.class.getResourceAsStream(filePath);

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try{
                is.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static int[][] GetLevelImages() {
        int[][] levelImages = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage levelImg = GetImage(testLevel);

        for (int j = 0; j < levelImg.getHeight(); j++) {
            for (int i = 0; i < levelImg.getWidth(); i++) {
                Color color = new Color(levelImg.getRGB(i, j));
                int value = color.getRed();

                if (value >= 48) {
                    value = 0;
                }
                levelImages[j][i] = value;
            }
        }

        return levelImages;
    }


}
