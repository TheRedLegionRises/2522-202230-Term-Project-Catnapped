package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;
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
    private BufferedImage img;
    private int animationTick, animationIndex, animationSpeed = 15;
    private static final int healthBarWidth = 66;
    private static final int healthBarHeight = 34;
    private static final int healthBarXCoordinate = 10;
    private static final int healthBarYCoordinate = 10;
    private static final float xDrawOffset = 20;
    private float yDrawOffset = 0;
    private boolean movementChanged, moveLeft, moveRight, jump = false;
    float tempXSpeed = 0, tempYSpeed = 0;
    private float airSpeed = 0f;
    private float gravitySpeed = 0.04f;
    private float jumpSpeed = -2.25f;
    private boolean playerInAir = false;
    private int currentPlayerAction = IDLE;
    private int[][] levelInfo;

    public Player(float newXCoordinate, float newYCoordinate, int newHeight, int newWidth) {
        super(newXCoordinate, newYCoordinate, newHeight, newWidth);
        loadPlayerAnimations();
        createHitbox(newXCoordinate, newYCoordinate, (int)32,(int) 32);
    }

    //Health Bar UI


    private void loadPlayerAnimations() {

        String[] arrayOfPlayerAnimations = {"/images/King_Mewrthur_Idle.png", "/images/King_Mewrthur_Run.png",
                "/images/King_Mewrthur_Jump.png", "/images/King_Mewrthur_Take_Damage.png",
                "/images/King_Mewrthur_Attack_1.png", "/images/King_Mewrthur_Death.png"};

        for (int i = 0; i < 6; i++) {
            img = LoadImages.GetImage(arrayOfPlayerAnimations[i]);
            BufferedImage[] currentAnimation;

            currentAnimation = new BufferedImage[GetPlayerAttribute(i)];

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
        g.drawImage(allAnimations[currentPlayerAction][animationIndex], (int) (actorHitbox.x - xDrawOffset),
                (int) (actorHitbox.y),
                width, height,null);
        drawActorHitbox(g);
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

        if(moveLeft == moveRight && !playerInAir) {
            currentPlayerAction = IDLE;
            return;
        }

        else  {
            currentPlayerAction = RUNNING;
            if (moveRight) {
                tempXSpeed = 1;

            } else if(moveLeft) {
                tempXSpeed = -1;
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

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public void setJump(boolean isPlayerJumping) {
        this.jump = isPlayerJumping;
    }

}
