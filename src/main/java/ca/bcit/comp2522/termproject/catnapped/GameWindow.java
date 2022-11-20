package ca.bcit.comp2522.termproject.catnapped;

import javax.swing.*;

public class GameWindow {

    private final JFrame jframe;

    public GameWindow(GamePanel newGamePanel) {

        jframe = new JFrame();

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(newGamePanel);
        //ALWAYS ON BOTTOM
        jframe.setLocationRelativeTo(null);
        jframe.setResizable(false); // Keeps frame 1 size (increases / decreases the difficulty of the game if so)
        jframe.pack(); //Finds the preferred size
        jframe.setVisible(true);

    }
}
