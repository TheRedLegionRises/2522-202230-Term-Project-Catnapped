package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;
import java.awt.image.BufferedImage;

import static ca.bcit.comp2522.termproject.catnapped.Constants.PlayerAttributes.*;
import static ca.bcit.comp2522.termproject.catnapped.HelperMethods.*;

/**
 * Player Class.
 * @author jerry
 * @version 2022
 */
public class Player extends Actor{

    private final String imageURL = "imageURLGoesHere";
    private BufferedImage[] idleAnimation, runningAnimation;
    private BufferedImage[][] allAnimations = new BufferedImage[6][];
    private BufferedImage img;
    private int animationTick, animationIndex, animationSpeed = 15;
    private float xDrawOffset = 40;
    private float yDrawOffset = 8;
    private boolean movementChanged, moveLeft, moveRight, moveUp, moveDown, jump = false;
    float tempXSpeed = 0, tempYSpeed = 0;
    private float airSpeed = 0f;
    private float gravitySpeed = 0.04f;
    private float jumpSpeed = -2.25f;
    private boolean playerInAir = false;
    private int currentPlayerAction = IDLE;
    private int[][] levelInfo;

    public Player(float newXCoordinate, float newYCoordinate, int newMaxHealth, int newHeight, int newWidth) {
        super(newXCoordinate, newYCoordinate, newMaxHealth, newHeight, newWidth);
        loadPlayerAnimations();
        createHitbox(newXCoordinate, newYCoordinate, 36, 40);
    }

    private void loadPlayerAnimations() {

        String[] arrayOfPlayerAnimations = {"/images/King_Mewrthur_Idle.png", "/images/King_Mewrthur_Run.png",
                "/images/King_Mewrthur_Jump.png", "/images/King_Mewrthur_Take_Damage.png",
                "/images/King_Mewrthur_Attack_1.png", "/images/King_Mewrthur_Death.png"};

        for (int i = 0; i < 6; i++) {
            img = LoadImages.GetImage(arrayOfPlayerAnimations[i]);
            BufferedImage[] currentAnimation = new BufferedImage[GetPlayerAttribute(i)]; // If 5 - get an error - care

            System.out.println("Completed i: "+ i);
            System.out.println("GetPlayerAttribute: " + GetPlayerAttribute(i));
            System.out.println("CurrentAnimation length: " + currentAnimation.length);

            for(int j = 0; j < currentAnimation.length; j++) {
                currentAnimation[j] = img.getSubimage(0, j * 16, img.getWidth(), 16); // Cat is 16 pixels tall and 32 pixels fat
            }
            allAnimations[i] = currentAnimation;
        }
    }

    public void loadLevelInfo(int[][] newLevelInfo) {
        this.levelInfo = newLevelInfo;
    }



    public void updatePlayer() {
        updatePos();
        updateAnimationThread();
    }

    public void renderPlayer(Graphics g) {
        g.drawImage(allAnimations[currentPlayerAction][animationIndex], (int) (playerHitbox.x - xDrawOffset),
                (int) (playerHitbox.y - yDrawOffset),
                width, height,null);
        drawPlayerHitbox(g);

    }

    private void updateAnimationThread() {
        if (movementChanged) {
            animationIndex = 0;
            movementChanged = false;
        }
        animationTick++;
        if(animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= allAnimations[currentPlayerAction].length)
                animationIndex = 0;
        }

    }

    private void updatePos() {

        if(jump) {
            playerJump();
        }

        if(moveLeft == moveRight && !playerInAir) {
            currentPlayerAction = IDLE;
            tempXSpeed = 0;
            return;
        }

//        if (moveLeft == moveRight) {
//            currentPlayerAction = IDLE;
//        }
        else  {
//            currentPlayerAction = RUNNING;
            if (moveRight) {
                tempXSpeed = 1;

            } else if(moveLeft) {
                tempXSpeed = -1;
            }
        }

        if(playerInAir) {
            if(collisionDetection(playerHitbox.x, playerHitbox.y+ airSpeed, playerHitbox.width,
                    playerHitbox.height, levelInfo)) {
                playerHitbox.y += airSpeed;
                airSpeed += gravitySpeed;
                updateXPosition(tempXSpeed);
            } else{
                playerHitbox.y = CheckActorCollisionWithCeilingOrFloor(playerHitbox, airSpeed);
                if (airSpeed > 0) {
                    resetIfFloating();
                } else{
                    //Fall faster after hitting something (e.g. roof)
                    airSpeed = 0.5f;
                    updateXPosition(tempXSpeed);
                }
            }
        }else {
            updateXPosition(tempXSpeed);
        }
        currentPlayerAction = RUNNING;


//        if (moveUp) {
//            tempYSpeed = -1;
////            currentPlayerAction = JUMPING;
//        } else if (moveDown) {
//            tempYSpeed = 1;
//        }

//        if(collisionDetection(playerHitbox.x + tempXSpeed, playerHitbox.y + tempYSpeed,
//                playerHitbox.width, playerHitbox.height, levelInfo)) {
//            playerHitbox.x += tempXSpeed;
//            playerHitbox.y += tempYSpeed;
//            currentPlayerAction = RUNNING;
//        }

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

    private void updateXPosition(float tempXSpeed) {
        if(collisionDetection(playerHitbox.x + tempXSpeed, playerHitbox.y,
                playerHitbox.width, playerHitbox.height, levelInfo)) {
            playerHitbox.x += tempXSpeed;
        }else {
            playerHitbox.x = GetActorNextToWall(playerHitbox, tempXSpeed);
        }
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    public void setJump(boolean isPlayerJumping) {
        this.jump = isPlayerJumping;
    }

}
