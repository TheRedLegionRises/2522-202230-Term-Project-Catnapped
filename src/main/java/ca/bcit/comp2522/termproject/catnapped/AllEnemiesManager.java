package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static ca.bcit.comp2522.termproject.catnapped.Constants.EnemyConstants.*;

public class AllEnemiesManager {
//    private Playing playing;
    private Game game;
    private InGame inGame;
    private static final int NUMBER_OF_ANIMATIONS = 5;
    private BufferedImage[][] allEnemyAnimations;
    private ArrayList<Enemy> listOfEnemies = new ArrayList<>();

    public AllEnemiesManager(InGame inGame) {
        this.inGame = inGame;
        loadEnemyImages();
        addEnemies();
    }

    private void addEnemies() {
        listOfEnemies = LoadImages.GetEnemies();
        System.out.println("Size of crabs: " + listOfEnemies.size());
    }

    public void updateEnemies(int[][] levelInfo) {
        for (Enemy eachEnemy : listOfEnemies) {
            eachEnemy.updateEnemy(levelInfo);
        }
    }

    public void renderEnemies(Graphics g) {
        for (Enemy enemy : listOfEnemies) {
            g.drawImage(allEnemyAnimations[enemy.getEnemyAction()][enemy.getAnimationIndex()],
                    (int) enemy.getHitbox().x - HITBOX_OFFSET_X, (int) enemy.getHitbox().y - HITBOX_OFFSET_Y,
                    ENEMY_WIDTH, ENEMY_HEIGHT, null);
            enemy.drawActorHitbox(g);
        }
    }

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

}
