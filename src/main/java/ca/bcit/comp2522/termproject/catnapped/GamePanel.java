package ca.bcit.comp2522.termproject.catnapped;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static ca.bcit.comp2522.termproject.catnapped.Constants.Directions.*;
import static ca.bcit.comp2522.termproject.catnapped.Constants.PlayerAttributes.*;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;

    private BufferedImage img, subImg;
    private BufferedImage[] idleAnimation, runAnimation, deathAnimation,
            jumpAnimation, attackAnimation, takeDamageAnimation;
    private int animationTick, animationIndex, animationSpeed = 15;
    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean moving = false;
    private float xDelta = 100, yDelta = 100;



    public GamePanel() {

        mouseInputs = new MouseInputs(this);

        importImg();
        loadAnimations();
        setPanelSize();

        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void loadAnimations() {

        idleAnimation = new BufferedImage[6]; // If 5 - get an error - care

        for(int i = 0; i < idleAnimation.length; i++)
            idleAnimation[i] = img.getSubimage(0 , i*16, 32 , 16); // Cat is 16 pixels tall and 32 pixels fat

    }

    private void importImg() {
        InputStream is4 = getClass().getResourceAsStream("/images/King_Mewrthur_Idle.png"); // - 1 slash to reach images
        InputStream is1 = getClass().getResourceAsStream("/images/King_Mewrthur_Death.png");
        InputStream is2 = getClass().getResourceAsStream("/images/King_Mewrthur_Attack_1.png");
        InputStream is3 = getClass().getResourceAsStream("/images/King_Mewrthur_Jump.png");
        InputStream is = getClass().getResourceAsStream("/images/King_Mewrthur_Run.png");
        InputStream is5 = getClass().getResourceAsStream("/images/King_Mewrthur_Take_Damage.png");


        try {img = ImageIO.read(is);
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

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800); // Good arcade game size
        setPreferredSize(size); // jFrame.pack() can find this

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        updateAnimationThread();
        setAnimation();
        updatePos();

//        subImg = img.getSubimage(0 , 0*16, 32 , 16);  To get images within an image
        g.drawImage(idleAnimation[animationIndex],0 , 0, 320, 160,null);

    }


    public void setDirection(int direction) {
        this.playerDir = direction;
        moving = true;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private void updateAnimationThread() {

        animationTick++;
        if(animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= GetPlayerAttribute(playerAction))
                animationIndex = 0;
        }

    }

    private void setAnimation() {
        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;
    }

    private void updatePos() {
        if (moving) {
            switch (playerDir) {
                case LEFT:
                    xDelta -= 5;
                    break;
                case UP:
                    yDelta -= 5;
                    break;
                case RIGHT:
                    xDelta += 5;
                    break;
                case DOWN:
                    yDelta += 5;
                    break;
            }
        }
    }
}
