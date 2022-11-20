package ca.bcit.comp2522.termproject.catnapped;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static ca.bcit.comp2522.termproject.catnapped.Constants.Directions.*;
import static ca.bcit.comp2522.termproject.catnapped.Constants.PlayerAttributes.*;

/**
 * Player Class.
 * @author jerry
 * @version 2022
 */
public class Player extends Actor{

    private final String imageURL = "imageURLGoesHere";
    private BufferedImage[] idleAnimation, runningAnimation;
    private BufferedImage[][] allAnimations;
    private BufferedImage img;
    private int animationTick, animationIndex, animationSpeed = 15;
    private int playerDir = -1;
    private boolean moving = false;
    private float xCoordinate = 100, yCoordinate = 100;
    private int currentPlayerAction = IDLE;

    public Player(float newXCoordinate, float newYCoordinate, int newMaxHealth, int newHeight, int newWidth) {
        super(newXCoordinate, newYCoordinate, newMaxHealth, newHeight, newWidth);
        loadPlayerAnimations();
    }

    private void loadPlayerAnimations() {
        InputStream is = getClass().getResourceAsStream("/images/King_Mewrthur_Idle.png"); // - 1 slash to reach images

        InputStream is1 = getClass().getResourceAsStream("/images/King_Mewrthur_Death.png");
        InputStream is2 = getClass().getResourceAsStream("/images/King_Mewrthur_Attack_1.png");
        InputStream is3 = getClass().getResourceAsStream("/images/King_Mewrthur_Jump.png");
        InputStream is4 = getClass().getResourceAsStream("/images/King_Mewrthur_Take_Damage.png");

        try {img = ImageIO.read(is);

            idleAnimation = new BufferedImage[6]; // If 5 - get an error - care
            runningAnimation = new BufferedImage[8];

            for(int i = 0; i < idleAnimation.length; i++) {
                idleAnimation[i] = img.getSubimage(0, i * 16, 32, 16); // Cat is 16 pixels tall and 32 pixels fat
            }

            for(int j = 0; j < runningAnimation.length; j++) {
                runningAnimation[j] = img.getSubimage(0, j * 16, 32, 16);
            }

            allAnimations = new BufferedImage[2][];
            allAnimations[0] = idleAnimation;
            allAnimations[1] = runningAnimation;
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try{
                is.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updatePosition() {
        updatePos();
        updateAnimationThread();

    }

    public void renderPlayer(Graphics g) {
        g.drawImage(allAnimations[currentPlayerAction][animationIndex],(int) xCoordinate , (int) yCoordinate, 160, 80,null);

    }

    private void updateAnimationThread() {
        animationTick++;
        if(animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= idleAnimation.length)
                animationIndex = 0;
        }

    }

    private void updatePos() {
        if (moving) {
            switch (playerDir) {
                case LEFT:
                    xCoordinate -= 1;
                    break;
                case UP:
                    yCoordinate -= 1;
                    break;
                case RIGHT:
                    xCoordinate += 1;
                    break;
                case DOWN:
                    yCoordinate += 1;
                    break;
            }
        }
    }

    public void setDirection(int direction) {
        this.playerDir = direction;
        moving = true;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

}
