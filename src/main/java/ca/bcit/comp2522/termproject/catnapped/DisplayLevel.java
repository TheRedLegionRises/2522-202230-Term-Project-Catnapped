package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static ca.bcit.comp2522.termproject.catnapped.LoadImages.TERRAIN_IMG;

/**
 * A DisplayLevel class. Renders the current level into our game window.
 * @author jerry and bryan
 * @version 2022
 */
public class DisplayLevel {
    private Game game;
    private BufferedImage[] levelImages;
    private ArrayList<LevelInfo> levels;
    private int lvlIndex = 0;

    /**
     * Constructor for our DisplayLevel class.
     * @param game a Game object
     */
    public DisplayLevel(Game game) {
        this.game = game;
        loadAllSprites();
        levels = new ArrayList<>();
        buildAllLevels();
    }
    /**
     * Loads next level for game
     */
    public void loadNextLevel() {
        lvlIndex++;
        if (lvlIndex >= levels.size()) {
            lvlIndex = 0;
            System.out.println("Null");
            Gamestate.state = Gamestate.MENU;
        }

        LevelInfo newLevel = levels.get(lvlIndex);
        game.getInGame().getEnemyManager().LoadEnemies(newLevel);
        game.getInGame().getPlayer().loadLevelInfo(newLevel.getLevelImage());
        game.getInGame().setMaxLvlOffsetX(newLevel.getLvlOffset());
    }
    /**
     * Loads all levels for game
     */
    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadImages.GetAllLevels();
        for(BufferedImage img : allLevels)
            levels.add(new LevelInfo(img));
    }

    /**
     * Loads all terrain information from an image into an array
     */
    private void loadAllSprites() {
        BufferedImage img = LoadImages.GetImage(TERRAIN_IMG);
        levelImages = new BufferedImage[48];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++) {
                int currentIndex = i * 12 + j;
                levelImages[currentIndex] = img.getSubimage(j * 32, i * 32, 32, 32);
            }
        }
    }

    /**
     * Renders the level into our game window.
     * @param g a Graphics object.
     */
    public void drawLevel(Graphics g, int xLvlOffset) {

        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < levels.get(lvlIndex).getLevelImage()[0].length; i++) {
                int index = levels.get(lvlIndex).getSpriteIndex(i, j);
                g.drawImage(levelImages[index], i * 32 - xLvlOffset, j * 32, 32, 32, null);
            }
        }
    }

    public void update() {

    }

    public LevelInfo getCurrentLevel() {
        return levels.get(lvlIndex);
    }

    // Might need later
    public int getAmountOfLevels() {
        return levels.size();

    }
}