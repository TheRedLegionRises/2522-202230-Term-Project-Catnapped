package ca.bcit.comp2522.termproject.catnapped;

//import javafx.scene.input.KeyEvent;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverScreen {
    private InGame ingame;
    public GameOverScreen(InGame newGameState) {
        this.ingame = newGameState;
    }

    public void drawOverlay(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WINDOW_WIDTH, Game.GAME_WINDOW_HEIGHT);

        g.setColor(Color.white);
        g.drawString("Game Over!", Game.GAME_WINDOW_WIDTH / 2, 150);
        g.drawString("Press Esc to exit to main menu", Game.GAME_WINDOW_WIDTH / 2, 200);
    }
    
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            ingame.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
    }
}
