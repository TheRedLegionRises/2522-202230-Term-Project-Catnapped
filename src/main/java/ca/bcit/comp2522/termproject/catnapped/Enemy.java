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
    private float attackRange = Game.DEFAULT_TILE_SIZE;
    private float viewRange = 5 * attackRange;

    public Enemy(float newXCoordinate, float newYCoordinate , int newHeight, int newWidth) {
        super(newXCoordinate, newYCoordinate, newHeight, newWidth);
        createHitbox(newXCoordinate, newYCoordinate, 20, 20);

    }

    public void updateEnemy(int[][] levelInfo) {
        updateMovement(levelInfo);
        updateAnimationThread();
    }

    private void firstUpdate(int[][] levelInfo) {
        if(firstUpdate) {
            if (!IsActorOnFloor(actorHitbox, levelInfo)) {
                isEnemyInAir = true;
            }
            firstUpdate = false;
        }
    }

    private void enemyInAir(int[][] levelInfo) {
        if(collisionDetection(actorHitbox.x, actorHitbox.y + fallSpeed,
                actorHitbox.width, actorHitbox.height, levelInfo)) {
            actorHitbox.y += fallSpeed;
            fallSpeed += gravity;
        } else{
            isEnemyInAir = false;
            actorHitbox.y = CheckActorCollisionWithCeilingOrFloor(actorHitbox, fallSpeed);
        }
    }

    private void enemyPatrol(int[][] levelInfo) {
        //If Enemy is Idle, set to Running
        switch(enemyAction) {
            case ENEMY_IDLE:
                changeEnemyAction(ENEMY_RUNNING);
                break;
            case ENEMY_RUNNING:
                float xSpeed = 0;

                //Sets direction
                if (walkDir == LEFT) {
                    xSpeed = -walkSpeed;
                } else{
                    xSpeed = walkSpeed;
                }
                //If Enemy has room to move and is on a floor tile, keep moving in that ddirection
                if(collisionDetection(actorHitbox.x + xSpeed, actorHitbox.y - 1,
                        actorHitbox.width, actorHitbox.height, levelInfo)) {
                    if(IsFloor(actorHitbox, xSpeed, levelInfo)) {
                        actorHitbox.x += xSpeed;
                        break;
                    }
                }
                changeWalkDir();
                break;
        }
    }

    private void changeEnemyAction(int newEnemyAction) {
        this.enemyAction = newEnemyAction;
        animationTick = 0;
        animationIndex = 0;
    }

    private void updateMovement(int[][] levelInfo) {
        if (firstUpdate) {
            firstUpdate(levelInfo);
        }

        if (isEnemyInAir) {
            enemyInAir(levelInfo);
        } else{
            enemyPatrol(levelInfo);
        }
    }

    private void changeWalkDir() {
        if(walkDir == LEFT) {
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
    }

    private boolean seePlayer(int[][] levelInfo, Player player) {
        int playerYPosition = (int) player.getHitbox().y / Game.DEFAULT_TILE_SIZE;
        int enemyYPosition = (int) y / Game.DEFAULT_TILE_SIZE;

        if (enemyYPosition == playerYPosition) {
            if (isPlayerInViewRange(player)) {
                if (ClearLineOfSight(levelInfo, actorHitbox, player.actorHitbox, enemyYPosition)) {
                    return true;
                }

            }
        }
        return false;
    }

    private boolean isPlayerInViewRange(Player player) {
        int distanceBetweenPlayerAndEnemy = (int) Math.abs(player.actorHitbox.x - actorHitbox.x);
        return distanceBetweenPlayerAndEnemy <= viewRange;
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
