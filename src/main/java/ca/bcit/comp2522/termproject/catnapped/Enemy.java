package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static ca.bcit.comp2522.termproject.catnapped.Constants.EnemyConstants.*;
import static ca.bcit.comp2522.termproject.catnapped.Constants.Directions.*;
import static ca.bcit.comp2522.termproject.catnapped.HelperMethods.*;

public class Enemy extends Actor{
    private int animationIndex, enemyAction = 0;
    private int animationTick, animationSpeed = 25;
    private boolean firstUpdate = true;
    private boolean isEnemyInAir = false;
    private float fallSpeed = 0f;
    private float gravity = 0.04f;
    private float walkSpeed = 0.5f;
    private int walkDir = LEFT;
    private static final int MAX_HEALTH = 1;
    private static final int DAMAGE_TO_PLAYERS = 1;
    private boolean alive = true;
    private int currentHealth = MAX_HEALTH;
    private float attackRange = Game.DEFAULT_TILE_SIZE;
    private float viewRange = 5 * attackRange;
    private Rectangle2D.Float enemyAttackBox;
    private boolean attackChecked;

    public Enemy(float newXCoordinate, float newYCoordinate , int newHeight, int newWidth) {
        super(newXCoordinate, newYCoordinate, newHeight, newWidth);
        createHitbox(newXCoordinate, newYCoordinate, 20, 20);
        createAttackHitbox();

    }

    private void createAttackHitbox() {
        //Add (* game.Scale) to widht and height when merging
        enemyAttackBox = new Rectangle2D.Float(this.x, this.y, 20, 40);
    }

    public void drawAttackBox(Graphics g) {
        g.setColor(Color.red);
        g.drawRect((int) enemyAttackBox.x, (int) enemyAttackBox.y,
                (int) enemyAttackBox.width, (int) enemyAttackBox.height);
    }


    public Rectangle2D.Float getEnemyAttackBox() {
        return enemyAttackBox;
    }

    public void updateEnemy(int[][] levelInfo, Player player) {
        updateMovement(levelInfo, player);
        updateAnimationThread();
        updateAttackBox();
    }

    private void updateAttackBox() {
        if(walkDir == RIGHT) {
            enemyAttackBox.x = actorHitbox.x + actorHitbox.width;
        } else if(walkDir == LEFT) {
            enemyAttackBox.x = actorHitbox.x - enemyAttackBox.width;
        }
        enemyAttackBox.y = actorHitbox.y + (actorHitbox.height - enemyAttackBox.height);
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
        float xSpeed = 0;

        //Sets direction
        if (walkDir == LEFT) {
            xSpeed = -walkSpeed;
        } else{
            xSpeed = walkSpeed;
        }
        //If Enemy has room to move and is on a floor tile, keep moving in that direction
        if(collisionDetection(actorHitbox.x + xSpeed, actorHitbox.y - 1,
                actorHitbox.width, actorHitbox.height, levelInfo)) {
            if(IsFloor(actorHitbox, xSpeed, levelInfo)) {
                actorHitbox.x += xSpeed;
                return;
            }
        }
        changeWalkDir();

    }

    private void changeEnemyAction(int newEnemyAction) {
        this.enemyAction = newEnemyAction;
        animationTick = 0;
        animationIndex = 0;
    }

    private void updateMovement(int[][] levelInfo, Player player) {
        if (firstUpdate) {
            firstUpdate(levelInfo);
        }

        if (isEnemyInAir) {
            enemyInAir(levelInfo);
        } else{
            switch(enemyAction) {
                case ENEMY_IDLE:
                    changeEnemyAction(ENEMY_RUNNING);
                    break;
                case ENEMY_RUNNING:
                    if (seePlayer(levelInfo, player)){
                        moveTowardsPlayer(player);
//                        System.out.println("I see you");
                    }
                    if (isPlayerInAttackRange(player)) {
                        changeEnemyAction(ENEMY_ATTACK);
                    }

                    enemyPatrol(levelInfo);
                    break;
                case ENEMY_ATTACK:
                    if(animationIndex == 0) {
                        attackChecked = false;
                    }
                    if(animationIndex == 2 && !attackChecked) {
                        checkEnemyHitPlayer(player);
                    }
                case ENEMY_DEATH:
            }
        }
    }

    private void checkEnemyHitPlayer(Player player) {
        if(enemyAttackBox.intersects(player.actorHitbox)) {
            player.playerGotHit(-1);
        }
        attackChecked = true;
    }

    private void changeWalkDir() {
        if(walkDir == LEFT) {
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
    }
    private void moveTowardsPlayer(Player player) {
        if(player.actorHitbox.x > this.actorHitbox.x) {
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
    }

    private boolean seePlayer(int[][] levelInfo, Player player) {
        int playerYPosition = (int) (player.getHitbox().y + player.height)/ Game.DEFAULT_TILE_SIZE;
        int enemyYPosition = (int) (this.actorHitbox.y) / Game.DEFAULT_TILE_SIZE;
//        System.out.println("Player Y: " + playerYPosition);
//        System.out.println("Enemy Y: " + enemyYPosition);

        if (enemyYPosition == playerYPosition) {
//            System.out.println("Within Range");
            if (isPlayerInViewRange(player)) {
//                System.out.println("I see you");
                if (ClearLineOfSight(levelInfo, this.actorHitbox, player.actorHitbox, enemyYPosition)) {
                    return true;
                }

            }
        }
        return false;
    }

    private boolean isPlayerInViewRange(Player player) {
        int distanceBetweenPlayerAndEnemy = (int) Math.abs(player.actorHitbox.x - this.actorHitbox.x) / 32;
//        System.out.println("Distance: " + distanceBetweenPlayerAndEnemy);
        return distanceBetweenPlayerAndEnemy <= viewRange;
    }
    
    private boolean isPlayerInAttackRange(Player player) {
        int distanceBetweenPlayerAndEnemy = (int) Math.abs(player.actorHitbox.x - actorHitbox.x);
        return distanceBetweenPlayerAndEnemy <= attackRange;
    }

    private void updateAnimationThread() {
        animationTick++;
        if(animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= GetEnemyAttribute(enemyAction)) {
                animationIndex = 0;

                switch (enemyAction) {
                    case ENEMY_ATTACK -> enemyAction = ENEMY_IDLE;
                    case ENEMY_DEATH -> alive = false;
                }
            }
        }

    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public int getEnemyAction() {
        return enemyAction;
    }

    public int flipWidth() {
        if(walkDir == RIGHT) {
            return -1;
        } else {
            return 1;
        }
    }

    public int flipXDrawOffset() {
        if(walkDir == RIGHT) {
            return 60;
        } else {
            return 0;
        }
    }


    public void getHitByPlayer() {
        currentHealth--;

        if(currentHealth <= 0) {
            changeEnemyAction(ENEMY_DEATH);
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void reset() {
        actorHitbox.x = x;
        actorHitbox.y = y;
        firstUpdate = true;
        currentHealth = MAX_HEALTH;
        changeEnemyAction(ENEMY_IDLE);
        alive = true;
        fallSpeed = 0;
    }
}
