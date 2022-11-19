package ca.bcit.comp2522.termproject.catnapped;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    public GamePanel() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawRect(100, 100, 200, 200);
//        g.setColor(Color.red);
    }
}
