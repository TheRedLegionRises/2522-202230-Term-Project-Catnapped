package ca.bcit.comp2522.termproject.catnapped;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static ca.bcit.comp2522.termproject.catnapped.Constants.Directions.*;
import static ca.bcit.comp2522.termproject.catnapped.Constants.Directions.DOWN;

/**
 * Player Class.
 * @author jerry
 * @version 2022
 */
public class Player extends Actor{

    private final String imageURL = "imageURLGoesHere";
    private BufferedImage[] idleAnimation;
    private BufferedImage img;
    private int animationTick, animationIndex, animationSpeed = 15;
    private int playerDir = -1;
    private boolean moving = false;
    private float xCoordinate = 100, yCoordinate = 100;

    public Player(float newXCoordinate, float newYCoordinate, int newMaxHealth, int newHeight, int newWidth) {
        super(newXCoordinate, newYCoordinate, newMaxHealth, newHeight, newWidth);
        loadPlayerAnimations();
    }

    private void loadPlayerAnimations() {
        InputStream is = getClass().getResourceAsStream("/images/King_Mewrthur_Idle.png"); // - 1 slash to reach images

        try {img = ImageIO.read(is);

            idleAnimation = new BufferedImage[6]; // If 5 - get an error - care

            for(int i = 0; i < idleAnimation.length; i++)
                idleAnimation[i] = img.getSubimage(0 , i*16, 32 , 16); // Cat is 16 pixels tall and 32 pixels fat
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
        g.drawImage(idleAnimation[animationIndex],(int) xCoordinate , (int) yCoordinate, 160, 80,null);

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
                    xCoordinate -= 5;
                    break;
                case UP:
                    yCoordinate -= 5;
                    break;
                case RIGHT:
                    xCoordinate += 5;
                    break;
                case DOWN:
                    yCoordinate += 5;
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
