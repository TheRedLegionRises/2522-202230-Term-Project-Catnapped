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
    private boolean running = false;
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

    public Player(float newXCoordinate, float newYCoordinate, int newHeight, int newWidth, InGame ingame) {
        super(newXCoordinate, newYCoordinate, newHeight, newWidth);
        this.ingame = ingame;
        loadPlayerAnimations();
        createHitbox(newXCoordinate, newYCoordinate, (int)32,(int) 32);
        createAttackBox();
    }

    private void createAttackBox() {
        attackBox = new Rectangle2D.Float(this.x, this.y, (int) 20 /* times Game.Scale*/, (int) 20 /* times Game.Scale*/);
    }

    private void updateAttackBox() {
        if(facingRight) {
            attackBox.x = actorHitbox.x + actorHitbox.width;
        } else if(facingLeft) {
            attackBox.x = actorHitbox.x - attackBox.width;
        }
        attackBox.y = actorHitbox.y + (actorHitbox.height - attackBox.height);
    }

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

    public void loadLevelInfo(int[][] newLevelInfo) {
        this.levelInfo = newLevelInfo;
    }



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

    private void checkAttack() {
        if(attackChecked || animationIndex != 4) {
            return;
        }else {
            attackChecked = true;
            ingame.checkPlayerHitEnemy();
        }
    }


    public void renderPlayer(Graphics g, int xLvlOffset) {
        g.drawImage(allAnimations[currentPlayerAction][animationIndex], (int) (actorHitbox.x + flipImage - xDrawOffset * flipImage * flipDrawOffsetMultiplier) - xLvlOffset,
                (int) (actorHitbox.y),
                width * flipImage, height,null);
//        g.drawImage(allAnimations[4][animationIndex], (int) (actorHitbox.x + flipImage - xDrawOffset * flipImage * flipDrawOffsetMultiplier),
//                (int) (actorHitbox.y),
//                width * flipImage, height,null);
        if (attacking) {
            g.setColor(Color.cyan);
            g.drawRect((int) attackBox.x, (int) attackBox.y, (int) attackBox.width * 2, (int) attackBox.height / 4);
            g.fillRect((int) attackBox.x, (int) attackBox.y, (int) attackBox.width * 2, (int) attackBox.height / 4);
        }
        drawActorHitbox(g, xLvlOffset);
        drawAttackBox(g);
        drawHealthBar(g);
        drawHearts(g);
    }

    private void drawAttackBox(Graphics g) {
        g.setColor(Color.red);
        g.drawRect((int) attackBox.x, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    private void drawHealthBar(Graphics g){
        g.drawImage(healthBarImage, healthBarXCoordinate, healthBarYCoordinate, healthBarWidth, healthBarHeight, null);
    }

    private void drawHearts(Graphics g) {
        for (int i = 0; i < currentHealth; i++) {
            g.drawImage(heartAnimation[heartAnimationIndex], heartXCoordinate + ((heartWidth + 4) * i), heartYCoordinate, drawHeartWidth, drawHeartHeight, null);
        }
    }

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

    private void resetAnimation() {
        animationTick = 0;
        animationIndex = 0;
    }



    private void playerJump() {
        if(playerInAir) {
            return;
        }
        playerInAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetIfFloating() {
        playerInAir = false;
        airSpeed = 0;
    }



    private void updatePos() {
        tempXSpeed = 0;


        if(jump) {
            playerJump();
        }
        if (!playerInAir) {
            if ((!moveLeft && !moveRight) || (moveRight && moveLeft))
                return;
        }
        tempXSpeed = 0;
        if(moveLeft == moveRight && !playerInAir) {

        if(moveLeft == moveRight && !playerInAir || attacking) {
            if (firstAttackAnimationReset) {
                animationIndex = 0;
                animationTick = 0;
                firstAttackAnimationReset = false;
            }
            currentPlayerAction = IDLE;
            return;
        }
//        else if (attacking) {
//            currentPlayerAction = ATTACK;
//        }

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
//            currentPlayerAction = JUMPING;
            if(collisionDetection(actorHitbox.x, actorHitbox.y + airSpeed, actorHitbox.width,
                    actorHitbox.height, levelInfo)) {
                actorHitbox.y += airSpeed;
                airSpeed += gravitySpeed;
                updateXPosition(tempXSpeed);
            } else{
                actorHitbox.y = CheckActorCollisionWithCeilingOrFloor(actorHitbox, airSpeed) + 31;
                if (airSpeed > 0) {
                    resetIfFloating();
                } else{
                    //Fall faster after hitting something (e.g. roof)
                    airSpeed = 0.5f;
                }
                updateXPosition(tempXSpeed);
            }
        }else {
            updateXPosition(tempXSpeed);
        }
    }


    private void updateXPosition(float tempXSpeed) {
        if(collisionDetection(actorHitbox.x + tempXSpeed, actorHitbox.y,
                actorHitbox.width, actorHitbox.height, levelInfo)) {
            actorHitbox.x += tempXSpeed;

        }else {
            actorHitbox.x = GetActorNextToWall(actorHitbox, tempXSpeed);
        }
    }

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

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public void setJump(boolean isPlayerJumping) {
        this.jump = isPlayerJumping;
    }

    public void setAttacking(boolean isAttacking) {
        this.attacking = isAttacking;
    }

    public boolean getFirstAttackReset() {
        return this.firstAttackAnimationReset;
    }

    public void setFirstAttackAnimationReset(boolean newValue) {
        this.firstAttackAnimationReset = newValue;
    }

    public Rectangle2D.Float getAttackBox() {
        return this.attackBox;
    }

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
