package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static ca.bcit.comp2522.termproject.catnapped.Constants.EnemyConstants.*;
import static ca.bcit.comp2522.termproject.catnapped.Constants.Directions.*;
import static ca.bcit.comp2522.termproject.catnapped.HelperMethods.*;

/**
 * An enemy class. Extends Actor class.
 * @author jerry and bryan
 * @version 2022
 */
public class Enemy extends Actor{
    private int animationIndex, enemyAction = 0;
    private int animationTick, animationSpeed = 25;
    private boolean firstUpdate = true;
    private boolean isEnemyInAir = false;
    private float fallSpeed = 0f;
    private static final float gravity = 0.04f;
    private static final float walkSpeed = 0.5f;
    private int walkDir = LEFT;
    private static final int MAX_HEALTH = 1;
    private static final int DAMAGE_TO_PLAYERS = 1;
    private boolean alive = true;
    private int currentHealth = MAX_HEALTH;
    private final float attackRange = Game.DEFAULT_TILE_SIZE;
    private final float viewRange = 5 * attackRange;
    private Rectangle2D.Float enemyAttackBox;
    private boolean attackChecked;

    /**
     * Constructor for our Enemy class.
     * @param newXCoordinate a float
     * @param newYCoordinate a float
     * @param newHeight an integer
     * @param newWidth an integer
     */
    public Enemy(float newXCoordinate, float newYCoordinate , int newHeight, int newWidth) {
        super(newXCoordinate, newYCoordinate, newHeight, newWidth);
        createHitbox(newXCoordinate, newYCoordinate, 20, 20);
        createAttackHitbox();

    }

    /**
     * Creates a hitbox for our enemy
     */
    private void createAttackHitbox() {
        enemyAttackBox = new Rectangle2D.Float(this.x, this.y, 20, 40);
    }

    /**
     * Draws the hitbox for our enemy (Used for testing purposes)
     * @param g a Graphics object
     */
    public void drawAttackBox(Graphics g) {
        g.setColor(Color.red);
        g.drawRect((int) enemyAttackBox.x, (int) enemyAttackBox.y,
                (int) enemyAttackBox.width, (int) enemyAttackBox.height);
    }


    /**
     * Returns the attack box of the enemy
     * @return a Rectangle2D.Float object
     */
    public Rectangle2D.Float getEnemyAttackBox() {
        return enemyAttackBox;
    }

    /**
     * Updates the current status of our enemy
     * @param levelInfo a 2D integer array
     * @param player a Player object
     */
    public void updateEnemy(int[][] levelInfo, Player player) {
        updateMovement(levelInfo, player);
        updateAnimationThread();
        updateAttackBox();
    }

    /**
     * Updates our attack box so that it is always directly in front of the enemy
     */
    private void updateAttackBox() {
        if(walkDir == RIGHT) {
            enemyAttackBox.x = actorHitbox.x + actorHitbox.width;
        } else if(walkDir == LEFT) {
            enemyAttackBox.x = actorHitbox.x - enemyAttackBox.width;
        }
        enemyAttackBox.y = actorHitbox.y + (actorHitbox.height - enemyAttackBox.height);
    }

    /**
     * Check if enemy is in air on the first update.
     * @param levelInfo a 2D integer array
     */
    private void firstUpdate(int[][] levelInfo) {
        if(firstUpdate) {
            if (!IsActorOnFloor(actorHitbox, levelInfo)) {
                isEnemyInAir = true;
            }
            firstUpdate = false;
        }
    }

    /**
     * Check if enemy is currently in the air
     * @param levelInfo a 2D integer array
     */
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

    /**
     * Makes enemy constantly patrol from left to right
     * @param levelInfo a 2D integer array
     */
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

    /**
     * Changes the enemy's current action
     * @param newEnemyAction an integer
     */
    private void changeEnemyAction(int newEnemyAction) {
        this.enemyAction = newEnemyAction;
        animationTick = 0;
        animationIndex = 0;
    }

    /**
     * Changes enemy movement depending on the circumstances
     * @param levelInfo a 2D integer array
     * @param player a Player object
     */

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
//                    if (seePlayer(levelInfo, player)){
//                        moveTowardsPlayer(player);
////                        System.out.println("I see you");
//                    }
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

    /**
     * Checks if enemy hits player, if they do, then subtract health from player
     * @param player Player object
     */
    private void checkEnemyHitPlayer(Player player) {
        if(enemyAttackBox.intersects(player.actorHitbox)) {
            player.playerGotHit(DAMAGE_TO_PLAYERS);
        }
        attackChecked = true;
    }

    /**
     * Changes the enemy's current walk direction
     */
    private void changeWalkDir() {
        if(walkDir == LEFT) {
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
    }

    /**
     * Move towards player. Full method of seeing player and moving towards them does not work, so it lies here unused.
     * @param player Player object
     */
    private void moveTowardsPlayer(Player player) {
        if(player.actorHitbox.x > this.actorHitbox.x) {
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
    }

    /**
     * Determines if the enemy can see player via clear line of sight and same y position. Does not work and could not
     * fix in time, so we left it out
     * @param levelInfo a 2D integer array
     * @param player a Player object
     * @return a boolean
     */
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

    /**
     * Used in determining if enemy sees player. Returns true if player is in enemy's view range.
     * @param player a Player object
     * @return a boolean
     */
    private boolean isPlayerInViewRange(Player player) {
        int distanceBetweenPlayerAndEnemy = (int) Math.abs(player.actorHitbox.x - this.actorHitbox.x) / Game.DEFAULT_TILE_SIZE;
//        System.out.println("Distance: " + distanceBetweenPlayerAndEnemy);
        return distanceBetweenPlayerAndEnemy <= viewRange;
    }

    /**
     * Determines if player is in attack range
     * @param player a Player object
     * @return a boolean
     */
    private boolean isPlayerInAttackRange(Player player) {
        int distanceBetweenPlayerAndEnemy = (int) Math.abs(player.actorHitbox.x - actorHitbox.x);
        return distanceBetweenPlayerAndEnemy <= attackRange;
    }

    /**
     * Method for updating each enemy's animations.
     */
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

    /**
     * Returns the current animationIndex
     * @return an integer
     */
    public int getAnimationIndex() {
        return animationIndex;
    }

    /**
     * Returns the current enemyAction
     * @return an integer
     */
    public int getEnemyAction() {
        return enemyAction;
    }

    /**
     * Flip the image's width if walking to the right/left.
     * @return an integer
     */
    public int flipWidth() {
        if(walkDir == RIGHT) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * Returns an integer for drawing the pig image such that the image aligns with the hitbox.
     * @return an integer
     */
    public int flipXDrawOffset() {
        if(walkDir == RIGHT) {
            return 60;
        } else {
            return 0;
        }
    }


    /**
     * Decrease enemy health if they were hit by player.
     */
    public void getHitByPlayer() {
        currentHealth--;

        if(currentHealth <= 0) {
            changeEnemyAction(ENEMY_DEATH);
        }
    }

    /**
     * Checks if enemy is alive
     * @return a boolean
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Resets all enemy stats if game is reset.
     */
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
