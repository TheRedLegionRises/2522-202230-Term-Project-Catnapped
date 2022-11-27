package ca.bcit.comp2522.termproject.catnapped;

public class Enemy extends Actor{
    private int animationIndex, enemyAction, enemyType;
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
        animationTick++;
        if(animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= 100)
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
