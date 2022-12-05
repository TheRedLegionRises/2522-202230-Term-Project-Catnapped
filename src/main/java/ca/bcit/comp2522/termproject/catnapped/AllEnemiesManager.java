package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static ca.bcit.comp2522.termproject.catnapped.Constants.EnemyConstants.*;

/**
 * AllEnemiesManager Class. Used to manage all enemies in the level.
 * @author jerry and bryan
 * @version 2022
 */
public class AllEnemiesManager {
//    private Playing playing;
//    private Game game;

    private InGame inGame;
    private static final int NUMBER_OF_ANIMATIONS = 5;
    private BufferedImage[][] allEnemyAnimations;
    private ArrayList<Enemy> listOfEnemies = new ArrayList<>();

    /**
     * Constructor for the AllEnemiesManager class.
     * @param inGame an Ingame object
     */
    public AllEnemiesManager(InGame inGame) {
        this.inGame = inGame;
        loadEnemyImages();
    }

    /**
     * A method that reads a file and loads the respective amount of enemies into the level.
     */

    public void LoadEnemies(LevelInfo level) {
        listOfEnemies = level.getEnemys();
    }

    /**
     * Calls the update method in the enemy class to update the current state of the enemy.
     * @param levelInfo a 2D integer array
     * @param player a Player object
     */
    public void updateEnemies(int[][] levelInfo, Player player) {
        boolean isActive = false;
        for (Enemy eachEnemy : listOfEnemies)
            if (eachEnemy.isAlive()) {
                eachEnemy.updateEnemy(levelInfo, player);
                isActive = true;
            }
        if(!isActive)
            inGame.setLevelComplete(true);
    }

    /**
     * Displays all enemies on the screen while they are alive.
     * @param g a Graphics object
     */

    public void renderEnemies(Graphics g, int xoffset) {
        for (Enemy enemy : listOfEnemies) {
            if (enemy.isAlive()) {
                Rectangle2D.Float currentEnemyAttackBox = enemy.getEnemyAttackBox();
                g.drawImage(allEnemyAnimations[enemy.getEnemyAction()][enemy.getAnimationIndex()],
                        (int) enemy.getHitbox().x - HITBOX_OFFSET_X + enemy.flipXDrawOffset(), (int) enemy.getHitbox().y - HITBOX_OFFSET_Y,
                        ENEMY_WIDTH * enemy.flipWidth(), ENEMY_HEIGHT, null);
//                enemy.drawActorHitbox(g);
//                enemy.drawAttackBox(g);
            }
        }
    }

    /**
     * Checks if player hits an enemy. Does so by using the intersect method inside Rectangle2D.Float Object class
     * @param playerAttackBox a Rectangle2D.Float object
     */
    public void checkPlayerHitsEnemy(Rectangle2D.Float playerAttackBox) {
        for(Enemy eachEnemy : listOfEnemies) {
            if(eachEnemy.isAlive()) {
                if (playerAttackBox.intersects(eachEnemy.getHitbox())) {
                    eachEnemy.getHitByPlayer();
                    return;
                }
            }
        }
    }

    /**
     * Loads all enemy animations from images into arrays.
     */
    private void loadEnemyImages() {
        allEnemyAnimations = new BufferedImage[NUMBER_OF_ANIMATIONS][];

        String[] arrayOfEnemyAnimations = {"/images/Pig_Idle.png", "/images/Pig_Run.png",
                "/images/Pig_Hit.png", "/images/Pig_Attack.png", "/images/Pig_Dead.png"};

        BufferedImage img;

        for (int i = 0; i < NUMBER_OF_ANIMATIONS; i++) {
            img = LoadImages.GetImage(arrayOfEnemyAnimations[i]);
            BufferedImage[] currentAnimation;

            currentAnimation = new BufferedImage[GetEnemyAttribute(i)]; // If 5 - get an error - care

            for (int j = 0; j < currentAnimation.length; j++) {
                currentAnimation[j] = img.getSubimage(j * ENEMY_ANIMATION_WIDTH, 0, ENEMY_ANIMATION_WIDTH, img.getHeight()); // Cat is 16 pixels tall and 32 pixels fat
            }
            allEnemyAnimations[i] = currentAnimation;
        }
    }

    /**
     * If player dies and game is reset, reset all enemies to their original positions and health.
     */
    public void resetAllEnemies() {
        for(Enemy eachEnemy : listOfEnemies) {
            eachEnemy.reset();
        }
    }
}
