package ca.bcit.comp2522.termproject.catnapped;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;

    private BufferedImage img, subImg;
    private BufferedImage[] idleAnimation, runAnimation, deathAnimation
            ,jumpAnimation, attackAnimation, takeDamageAnimation;
    private int animationTick, animationIndex, animationSpeed = 15;
    
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
        InputStream is = getClass().getResourceAsStream("/images/King_Mewrthur_Idle.png"); // - 1 slash to reach images

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
        
//        subImg = img.getSubimage(0 , 0*16, 32 , 16);  To get images within an image
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
}
