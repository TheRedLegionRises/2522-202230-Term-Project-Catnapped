package ca.bcit.comp2522.termproject.catnapped;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import static ca.bcit.comp2522.termproject.catnapped.Constants.EnemyConstants.*;

/**
 * LoadImages helper class. Loads all images into an array.
 * @author jerry and bryan
 * @version 2022
 */
public class LoadImages {
    public static final String TERRAIN_IMG = "/images/Terrain.png";
    public static final String TEST_LEVEL_LONGER = "/levels/1.png";
    public static final String MENU_BUTTONS = "/images/menu_buttons.png";
    public static final String MENU_BG = "/images/menu_background.png";
    public static final String PAUSE_MENU = "/images/pause_menu.png";
    public static final String SOUND_BUTTONS = "/images/sound_button.png";
    public static final String UTIL_BUTTONS = "/images/util_buttons.png";
    public static final String VOLUME_BUTTONS = "/images/volume_buttons.png";
    public static final String MENU_BACKGROUND = "/images/background_image.jpg";
    public static final String LIFE_BAR = "/images/Live_Bar.png";
    public static final String HEART = "/images/Big_Heart_Idle.png";
    public static final String COMPLETE_OVERLAY = "/images/completed_menu.png";
    public static final String LEVEL_2 = "/levels/2.png";


    /**
     * Returns an image based on a filePath.
     * @param filePath a String URL
     * @return a BufferedImage object
     */
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

    /**
     * Get a list of all enemies based on the level schema.
     * @return
     */
    public static BufferedImage[] GetAllLevels() {
        URL url = LoadImages.class.getResource("/levels");
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[] files = file.listFiles();
        File[] filesSort = new File[files.length];

        for (int i = 0; i < filesSort.length; i++)
            for (int j = 0; j < files.length; j++) {
                if (files[j].getName().equals((i + 1) + ".png"))
                    filesSort[i] = files[j];

            }
        BufferedImage[] imgs = new BufferedImage[filesSort.length];

        for (int i = 0; i < imgs.length; i++)
            try {
                imgs[i] = ImageIO.read(filesSort[i]);
            } catch (IOException e) {
                e.printStackTrace();
        }
        return imgs;
    }

    /**
     * Puts data from a level schema into a 2D array for future rendering.
     * @return a 2D integer array
     */


    public static ArrayList<Enemy> GetEnemies() {
        BufferedImage levelImg = GetImage(TEST_LEVEL_LONGER);
        ArrayList<Enemy> enemyList = new ArrayList<>();

        for (int j = 0; j < levelImg.getHeight(); j++) {
            for (int i = 0; i < levelImg.getWidth(); i++) {
                Color color = new Color(levelImg.getRGB(i, j));
                int value = color.getGreen();

                if (value == ENEMY_IDENTIFIER) {
                    enemyList.add(new Enemy(i * Game.DEFAULT_TILE_SIZE, j * Game.DEFAULT_TILE_SIZE, ENEMY_HEIGHT, ENEMY_WIDTH));
                }
            }
        }
        return enemyList;

    }

    /**
     * Puts data from a level schema into a 2D array for future rendering.
     * @return a 2D integer array
     */

    public static int[][] GetLevelImages(BufferedImage img) {

        int[][] levelImages = new int[img.getHeight()][img.getWidth()];

        for (int j = 0; j < img.getHeight(); j++) {

            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
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
