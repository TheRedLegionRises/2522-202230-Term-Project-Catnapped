package ca.bcit.comp2522.termproject.catnapped;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static ca.bcit.comp2522.termproject.catnapped.Constants.EnemyConstants.*;

public class LoadImages {
    public static final String TERRAIN_IMG = "/images/Terrain.png";

    public static final String TEST_LEVEL = "/images/level_one_data.png";
    public static final String TEST_LEVEL_LONGER = "/images/test_level.png";
    public static final String MENU_BUTTONS = "/images/menu_buttons.png";
    public static final String MENU_BG = "/images/menu_background.png";
    public static final String PAUSE_MENU = "/images/pause_menu.png";
    public static final String SOUND_BUTTONS = "/images/sound_button.png";
    public static final String UTIL_BUTTONS = "/images/util_buttons.png";
    public static final String VOLUME_BUTTONS = "/images/volume_buttons.png";

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

    public static int[][] GetLevelImages() {

        BufferedImage levelImg = GetImage(TEST_LEVEL_LONGER);
        int[][] levelImages = new int[levelImg.getHeight()][levelImg.getWidth()];

        for (int j = 0; j < levelImg.getHeight(); j++) {
//            System.out.print("J: " + j);
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
