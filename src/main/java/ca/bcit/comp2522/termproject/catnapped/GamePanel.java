package ca.bcit.comp2522.termproject.catnapped;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static ca.bcit.comp2522.termproject.catnapped.Constants.PlayerAttributes.IDLE;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean moving = false;

    private Game game;
    public GamePanel(Game game) {
        this.game = game;

        mouseInputs = new MouseInputs(this);

        setPanelSize();

        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800); // Good arcade game size
        setPreferredSize(size); // jFrame.pack() can find this

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public void setDirection(int direction) {
        this.playerDir = direction;
        moving = true;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }
}
