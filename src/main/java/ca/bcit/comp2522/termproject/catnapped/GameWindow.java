package ca.bcit.comp2522.termproject.catnapped;

import javax.swing.*;

public class GameWindow {
    private static final int DEFAULT_HEIGHT = 400;
    private static final int DEFAULT_WIDTH = 400;
    private final JFrame jframe;

    public GameWindow(GamePanel newGamePanel) {

        jframe = new JFrame();

        jframe.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(newGamePanel);
        //ALWAYS ON BOTTOM
        jframe.setVisible(true);

    }
}
