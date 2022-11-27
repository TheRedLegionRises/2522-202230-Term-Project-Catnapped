package ca.bcit.comp2522.termproject.catnapped;

import static ca.bcit.comp2522.termproject.catnapped.Constants.EnemyConstants.*;
import static ca.bcit.comp2522.termproject.catnapped.Constants.Directions.*;
import static ca.bcit.comp2522.termproject.catnapped.HelperMethods.*;

public class Enemy extends Actor{
    private int animationIndex, enemyAction = 0, enemyType;
    private int animationTick, animationSpeed = 25;
    private boolean firstUpdate = true;
    private boolean isEnemyInAir = false;
    private float fallSpeed = 0f;
    private float gravity = 0.04f;
    private float walkSpeed = 1.0f;
    private int walkDir = LEFT;

    public Enemy(float newXCoordinate, float newYCoordinate, int newMaxHealth, int newHeight, int newWidth) {
        super(newXCoordinate, newYCoordinate, newMaxHealth, newHeight, newWidth);
        createHitbox(newXCoordinate, newYCoordinate, 20, 20);

    }

    public void updateEnemy(int[][] levelInfo) {
        updateMovement(levelInfo);
        updateAnimationThread();
    }

    private void updateMovement(int[][] levelInfo) {
        if(firstUpdate) {
            if (!IsActorOnFloor(actorHitbox, levelInfo)) {
//                System.out.println("InAir: " + isEnemyInAir);
                isEnemyInAir = true;
            }
            firstUpdate = false;
        }
        if (isEnemyInAir) {
            if(collisionDetection(actorHitbox.x, actorHitbox.y + fallSpeed, actorHitbox.width, actorHitbox.height, levelInfo)) {
                actorHitbox.y += fallSpeed;
                fallSpeed += gravity;
            } else{
                isEnemyInAir = false;
                actorHitbox.y = CheckActorCollisionWithCeilingOrFloor(actorHitbox, fallSpeed);
            }
        } else{
                switch(enemyAction) {
                    case ENEMY_IDLE:
                        enemyAction = ENEMY_RUNNING;
                        break;
                    case ENEMY_RUNNING:
    //                    System.out.println("Running");
                        float xSpeed = 0;

                        if (walkDir == LEFT) {
                            xSpeed = -walkSpeed;
    //                        System.out.println("Walkir Left works. XSpeed: " + xSpeed);
                        } else{
                            xSpeed = walkSpeed;
                        }

                        if(collisionDetection(actorHitbox.x + xSpeed, actorHitbox.y - 1, actorHitbox.width, actorHitbox.height, levelInfo)) {
    //                        System.out.println("Does this Work");
                            if(IsFloor(actorHitbox, xSpeed, levelInfo)) {
                                actorHitbox.x += xSpeed;
                                break;
                            }
                        }
                        changeWalkDir();
                        break;
            }

        }
    }

    private void changeWalkDir() {
        if(walkDir == LEFT) {
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
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
