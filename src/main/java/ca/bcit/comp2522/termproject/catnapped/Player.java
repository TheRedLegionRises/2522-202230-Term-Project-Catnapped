package ca.bcit.comp2522.termproject.catnapped;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

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
        updateAnimationThread();

    }

    public void renderPlayer(Graphics g) {
        g.drawImage(idleAnimation[animationIndex],0 , 0, 160, 80,null);

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

    public String getImageLocation() {
        return this.imageURL;
    }
}
