package ca.bcit.comp2522.termproject.catnapped;

import javax.swing.*;

/**
 * GameWindow class. Displays the inside of the game inside the frame.
 * @author Jerry and Bryan
 * @version 2022
 */
public class GameWindow {
    private final JFrame jframe;

    /**
     * Constructor for our GameWindow class.
     * @param newGamePanel
     */
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
