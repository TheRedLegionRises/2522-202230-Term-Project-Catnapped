package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;
import java.awt.image.BufferedImage;

import static ca.bcit.comp2522.termproject.catnapped.Constants.PlayerAttributes.*;

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
    private int playerDir = -1;
    private boolean moving, moveLeft, moveRight, moveUp, moveDown = false;
    private float xCoordinate = 100, yCoordinate = 100;
    private int currentPlayerAction = IDLE;

    public Player(float newXCoordinate, float newYCoordinate, int newMaxHealth, int newHeight, int newWidth) {
        super(newXCoordinate, newYCoordinate, newMaxHealth, newHeight, newWidth);
        loadPlayerAnimations();
    }

    private void loadPlayerAnimations() {
//        InputStream is = getClass().getResourceAsStream("/images/King_Mewrthur_Idle.png"); // - 1 slash to reach images
//        InputStream is1 = getClass().getResourceAsStream("/images/King_Mewrthur_Run.png");
//        InputStream is2 = getClass().getResourceAsStream("/images/King_Mewrthur_Jump.png");
//        InputStream is3 = getClass().getResourceAsStream("/images/King_Mewrthur_Take_Damage.png");
//        InputStream is4 = getClass().getResourceAsStream("/images/King_Mewrthur_Attack_1.png");
//        InputStream is5 = getClass().getResourceAsStream("/images/King_Mewrthur_Death.png");

//        try {img = ImageIO.read(is1);
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

//            img = LoadImages.GetImage(LoadImages.PLAYER_RUN);
//            runningAnimation = new BufferedImage[8]; // If 5 - get an error - care
//
//            for(int i = 0; i < runningAnimation.length; i++) {
//                runningAnimation[i] = img.getSubimage(0, i * 16, 32, 16); // Cat is 16 pixels tall and 32 pixels fat
//            }

//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        finally {
//            try{
//                is.close();
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public void updatePosition() {
        updatePos();
        updateAnimationThread();

    }

    public void renderPlayer(Graphics g) {
        g.drawImage(allAnimations[currentPlayerAction][animationIndex],(int) xCoordinate , (int) yCoordinate,
                160, 80,null);

    }

    private void updateAnimationThread() {
        animationTick++;
        if(animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= allAnimations[currentPlayerAction].length)
                animationIndex = 0;
        }

    }

    private void updatePos() {

        if (moveLeft == moveRight) {
            currentPlayerAction = IDLE;
        } else if (moveRight) {
            xCoordinate += 1;
            currentPlayerAction = RUNNING;
        } else {
            xCoordinate -= 1;
            currentPlayerAction = RUNNING;
        }

        if (moveUp) {
            yCoordinate -= 1;
            currentPlayerAction = JUMPING;
        } else if (moveDown) {
            yCoordinate += 1;
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

    public void setCurrentPlayerAction(int newPlayerAction) {
        this.currentPlayerAction = newPlayerAction;
    }
}
