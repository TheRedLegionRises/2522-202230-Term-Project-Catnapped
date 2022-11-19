package ca.bcit.comp2522.termproject.catnapped;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private int frames = 0;
    private long lastFrame = 0;
    public GamePanel() {

        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawRect(100, 100, 200, 200);
//        g.setColor(Color.red);
    }
}
