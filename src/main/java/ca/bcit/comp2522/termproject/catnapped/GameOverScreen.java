package ca.bcit.comp2522.termproject.catnapped;

//import javafx.scene.input.KeyEvent;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * GameOverScreen class.
 * @author jerry and bryan
 * @version 2022
 */
public class GameOverScreen {
    private InGame ingame;

    /**
     * Constructor for our GameOverScreen class.
     * @param newGameState an InGame object
     */
    public GameOverScreen(InGame newGameState) {
        this.ingame = newGameState;
    }

    /**
     * Draws the GameOverScreen over the game window.
     * @param g a Graphics object
     */
    public void drawOverlay(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WINDOW_WIDTH, Game.GAME_WINDOW_HEIGHT);

        g.setColor(Color.white);
        g.drawString("Game Over!", Game.GAME_WINDOW_WIDTH / 2, 150);
        g.drawString("Press Esc to exit to main menu", Game.GAME_WINDOW_WIDTH / 2, 200);
    }

    /**
     * Determines if Esc is pressed, and returns player to home screen if it is pressed
     * @param e a KeyEvent object
     */
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            ingame.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
    }
}
