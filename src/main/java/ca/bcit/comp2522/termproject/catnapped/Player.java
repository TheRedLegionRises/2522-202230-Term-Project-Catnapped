package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static ca.bcit.comp2522.termproject.catnapped.Constants.PlayerAttributes.*;
import static ca.bcit.comp2522.termproject.catnapped.HelperMethods.*;

/**
 * Player Class.
 * @author Jerry and Bryan
 * @version 2022
 */
public class Player extends Actor{

    private BufferedImage[][] allAnimations = new BufferedImage[7][];
    private BufferedImage animationImage, healthBarImage, heartImage;
    private int heartAnimationLength = 8;
    private BufferedImage[] heartAnimation = new BufferedImage[heartAnimationLength];
    private int animationTick = 0, heartAnimationIndex = 0, animationIndex, animationSpeed = 15;
    private static final int HEALTH_SYSTEM_SCALE = 2;
    private static final int healthBarWidth = 66 * HEALTH_SYSTEM_SCALE;
    private static final int healthBarHeight = 34 * HEALTH_SYSTEM_SCALE;
    private static final int healthBarXCoordinate = 10;
    private static final int healthBarYCoordinate = 10;

    private static final int maxHealth = 3;
    private static int currentHealth = maxHealth;
    private static final int heartHeight = 14;
    private static final int heartWidth = 18;
    private static final int drawHeartHeight = heartHeight * HEALTH_SYSTEM_SCALE;
    private static final int drawHeartWidth = heartWidth * HEALTH_SYSTEM_SCALE;
    private static final int heartXCoordinate = 30;
    private static final int heartYCoordinate = healthBarHeight / 2 - 4;

    //Attack Varibles
    private Rectangle2D.Float attackBox;
    private int flipImage = 1;
    private int flipDrawOffset = 1;
    private int flipDrawOffsetMultiplier = 1;

    private static final float xDrawOffset = 20;
    private float yDrawOffset = 0;
    private boolean movementChanged, moveLeft, moveRight, jump = false;
    private boolean facingLeft = false, facingRight = true;
    float tempXSpeed = 0, tempYSpeed = 0;
    private float airSpeed = 0f;
    private float gravitySpeed = 0.04f;
    private float jumpSpeed = -2.25f;
    private boolean playerInAir = false;
    private int currentPlayerAction = IDLE;
    private int[][] levelInfo;
    private boolean attacking = false;
    private boolean firstAttackAnimationReset = false;
    private boolean attackChecked = false;
    private InGame ingame;
    private static final int HITBOX_WIDTH = 32;
    private static final int HITBOX_HEIGHT = 32;
    private static final int ATTACK_BOX_HEIGHT = 20;
    private static final int ATTACK_BOX_WIDTH = 20;

    /**
     * Constructor for our Player object
     * @param newXCoordinate a float
     * @param newYCoordinate a float
     * @param newHeight an integer
     * @param newWidth an integer
     * @param ingame an InGame object
     */
    public Player(float newXCoordinate, float newYCoordinate, int newHeight, int newWidth, InGame ingame) {
        super(newXCoordinate, newYCoordinate, newHeight, newWidth);
        this.ingame = ingame;
        loadPlayerAnimations();
        createHitbox(newXCoordinate, newYCoordinate, HITBOX_WIDTH,HITBOX_HEIGHT);
        createAttackBox();
    }

    /**
     * Creates the player's attack box
     */
    private void createAttackBox() {
        attackBox = new Rectangle2D.Float(this.x, this.y, ATTACK_BOX_WIDTH, ATTACK_BOX_HEIGHT);
    }

    /**
     * Updates the attack box so it is always directly in front of the player
     */
    private void updateAttackBox() {
        if(facingRight) {
            attackBox.x = actorHitbox.x + actorHitbox.width;
        } else if(facingLeft) {
            attackBox.x = actorHitbox.x - attackBox.width;
        }
        attackBox.y = actorHitbox.y + (actorHitbox.height - attackBox.height);
    }

    /**
     * Loads all player animations from images into a 2D array of BufferedImage[][]
     */
    private void loadPlayerAnimations() {

        String[] arrayOfPlayerAnimations = {"/images/King_Mewrthur_Idle.png", "/images/King_Mewrthur_Run.png",
                "/images/King_Mewrthur_Jump.png", "/images/King_Mewrthur_Take_Damage.png",
                "/images/King_Mewrthur_Attack_1.png", "/images/King_Mewrthur_Death.png"};

        for (int i = 0; i < 6; i++) {
            animationImage = LoadImages.GetImage(arrayOfPlayerAnimations[i]);
            BufferedImage[] currentAnimation;

            currentAnimation = new BufferedImage[GetPlayerAttribute(i)];

            for(int j = 0; j < currentAnimation.length; j++) {
                currentAnimation[j] = animationImage.getSubimage(0, j * 16, animationImage.getWidth(), 16); // Cat is 16 pixels tall and 32 pixels fat
            }
            allAnimations[i] = currentAnimation;
        }

        healthBarImage = LoadImages.GetImage(LoadImages.LIFE_BAR);
        heartImage = LoadImages.GetImage(LoadImages.HEART);

        for (int k = 0; k < heartAnimation.length; k++) {
            heartAnimation[k] = heartImage.getSubimage(k * heartWidth, 0, heartWidth, heartImage.getHeight());
        }
    }

    /**
     * Loads the current level information
     * @param newLevelInfo 2D integer array
     */
    public void loadLevelInfo(int[][] newLevelInfo) {
        this.levelInfo = newLevelInfo;
    }


    /**
     * Updates the player's current status.
     */
    public void updatePlayer() {
        if(currentHealth <= 0) {
            ingame.setGameOver(true);
            return;
        }
//        updateHealthBar();
        updateAttackBox();

        updatePos();
        if(attacking) {
            checkAttack();
        }
        updateAnimationThread();
//        setPlayerAnimation();
    }

    /**
     * Check if an attack was already checked or not
     */
    private void checkAttack() {
        if(attackChecked || animationIndex != 4) {
            return;
        }else {
            attackChecked = true;
            ingame.checkPlayerHitEnemy();
        }
    }

    /**
     * Displays the player onto the screen as well as Health Bar status
     * @param g Graphics object
     */
    public void renderPlayer(Graphics g) {
        g.drawImage(allAnimations[currentPlayerAction][animationIndex], (int) (actorHitbox.x + flipImage - xDrawOffset * flipImage * flipDrawOffsetMultiplier),
                (int) (actorHitbox.y),
                width * flipImage, height,null);
//        g.drawImage(allAnimations[4][animationIndex], (int) (actorHitbox.x + flipImage - xDrawOffset * flipImage * flipDrawOffsetMultiplier),
//                (int) (actorHitbox.y),
//                width * flipImage, height,null);
        if (attacking) {
            g.setColor(Color.cyan);
            g.drawRect((int) attackBox.x, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height / 4);
            g.fillRect((int) attackBox.x, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height / 4);
        }
//        drawActorHitbox(g);
//        drawAttackBox(g);
        drawHealthBar(g);
        drawHearts(g);
    }

    /**
     * Draws the attack box onto the screen. Used for testing purposes
     * @param g
     */
    private void drawAttackBox(Graphics g) {
        g.setColor(Color.red);
        g.drawRect((int) attackBox.x, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    /**
     * Draw the health bar
     * @param g a Graphics object
     */
    private void drawHealthBar(Graphics g){
        g.drawImage(healthBarImage, healthBarXCoordinate, healthBarYCoordinate, healthBarWidth, healthBarHeight, null);
    }

    /**
     * Draw the hearts for the health
     * @param g a Graphics object
     */
    private void drawHearts(Graphics g) {
        for (int i = 0; i < currentHealth; i++) {
            g.drawImage(heartAnimation[heartAnimationIndex], heartXCoordinate + ((heartWidth + 4) * i), heartYCoordinate, drawHeartWidth, drawHeartHeight, null);
        }
    }

    /**
     * Updates the animations for the player
     */
    private void updateAnimationThread() {
        animationTick++;
        if(animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            heartAnimationIndex++;
            if (heartAnimationIndex >= heartAnimation.length) {
                heartAnimationIndex = 0;
            }
            if(animationIndex >= GetPlayerAttribute(currentPlayerAction)) {
                animationIndex = 0;
                attackChecked = false;
                attacking = false;
            }
        }

    }

    /**
     * If player jumps, set playerInAir to true and current airSpeed to jumpSpeed
     */
    private void playerJump() {
        if(playerInAir) {
            return;
        }
        playerInAir = true;
        airSpeed = jumpSpeed;
    }

    /**
     * If player is not in air, set playerInAir to false and airspeed to 0
     */
    private void resetIfFloating() {
        playerInAir = false;
        airSpeed = 0;
    }


    /**
     * Updates the player's current position
     */
    private void updatePos() {
        tempXSpeed = 0;

        if(jump) {
            playerJump();
        }

        if(moveLeft == moveRight && !playerInAir || attacking) {
            if (firstAttackAnimationReset) {
                animationIndex = 0;
                animationTick = 0;
                firstAttackAnimationReset = false;
            }
            currentPlayerAction = IDLE;
            return;
        }

        else  {
            currentPlayerAction = RUNNING;
            if (moveRight) {
                tempXSpeed = 1;
                flipImage = 1;
                flipDrawOffsetMultiplier = 1;
                facingLeft = false;
                facingRight = true;

            } else if(moveLeft) {
                tempXSpeed = -1;
                flipImage = -1;
                flipDrawOffsetMultiplier = 3;
                facingRight = false;
                facingLeft = true;
            }
        }

        if (!playerInAir) {
            if(!IsActorOnFloor(actorHitbox, levelInfo)) {
                playerInAir = true;
            }
        }

        if(playerInAir) {
            if(collisionDetection(actorHitbox.x, actorHitbox.y + airSpeed, actorHitbox.width,
                    actorHitbox.height, levelInfo)) {
                actorHitbox.y += airSpeed;
                airSpeed += gravitySpeed;
                updateXPosition(tempXSpeed);
            } else{
                actorHitbox.y = CheckActorCollisionWithCeilingOrFloor(actorHitbox, airSpeed) +
                        (Game.DEFAULT_TILE_SIZE - 1);
                if (airSpeed > 0) {
                    resetIfFloating();
                } else{
                    //Fall faster after hitting something (e.g. roof)
                    airSpeed = 0.0f;
                }
                updateXPosition(tempXSpeed);
            }
        }else {
            updateXPosition(tempXSpeed);
        }
    }

    /**
     * Determines if the player can move in a certain direction
     * @param tempXSpeed a float
     */
    private void updateXPosition(float tempXSpeed) {
        if(collisionDetection(actorHitbox.x + tempXSpeed, actorHitbox.y,
                actorHitbox.width, actorHitbox.height, levelInfo)) {
            actorHitbox.x += tempXSpeed;

        }else {
            actorHitbox.x = GetActorNextToWall(actorHitbox, tempXSpeed);
        }
    }

    /**
     * etermines if a player gets hit by an object and increase/decrease health by that amount
     * @param amount an integer
     */
    public void playerGotHit(int amount) {
        currentHealth += amount;

        if(currentHealth <= 0) {
            currentHealth = 0;
//            gameOver();
        }
        if(currentHealth >= maxHealth) {
            currentHealth = maxHealth;
        }
    }

    /**
     * Set variable moveLeft
     * @param moveLeft a boolean
     */
    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    /**
     * Set variable moveRight
     * @param moveRight a boolean
     */
    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    /**
     * Set variable setJump
     * @param isPlayerJumping a boolean
     */
    public void setJump(boolean isPlayerJumping) {
        this.jump = isPlayerJumping;
    }

    /**
     * Set variable Attacking
     * @param isAttacking a boolean
     */
    public void setAttacking(boolean isAttacking) {
        this.attacking = isAttacking;
    }

    /**
     * Get variable firstAttackAnimationReset
     * @return a boolean
     */
    public boolean getFirstAttackReset() {
        return this.firstAttackAnimationReset;
    }

    /**
     * Set variable firstAttackAnimationRest
     * @param newValue a variable
     */
    public void setFirstAttackAnimationReset(boolean newValue) {
        this.firstAttackAnimationReset = newValue;
    }

    /**
     * Returns the player's attack box
     * @return a Rectangle2D.Float object
     */
    public Rectangle2D.Float getAttackBox() {
        return this.attackBox;
    }

    /**
     * Resets the player's current position, health and everything else after a new game is created.
     */
    public void resetAll() {
        moveRight = false;
        moveLeft = false;
        jump = false;
        playerInAir = false;
        attacking = false;
        currentPlayerAction = IDLE;
        currentHealth = maxHealth;

        actorHitbox.x = this.x;
        actorHitbox.y = this.y;

        if(!IsActorOnFloor(actorHitbox, levelInfo)) {
            playerInAir = true;
        }


    }
}
