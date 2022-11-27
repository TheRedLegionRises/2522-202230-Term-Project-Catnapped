package ca.bcit.comp2522.termproject.catnapped;

import static ca.bcit.comp2522.termproject.catnapped.Constants.EnemyConstants.*;

public class Enemy extends Actor{
    private int animationIndex, enemyAction = 0, enemyType;
    private int animationTick, animationSpeed = 25;

    public Enemy(float newXCoordinate, float newYCoordinate, int newMaxHealth, int newHeight, int newWidth) {
        super(newXCoordinate, newYCoordinate, newMaxHealth, newHeight, newWidth);
        createHitbox(newXCoordinate, newYCoordinate, newWidth, newHeight);

    }

    public void updateEnemy() {
        updateAnimationThread();
    }

    private void updateAnimationThread() {
        animationTick++;
        if(animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= GetEnemyAttribute(enemyAction))
                animationIndex = 0;
        }

    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public int getEnemyAction() {
        return enemyAction;
    }

}
