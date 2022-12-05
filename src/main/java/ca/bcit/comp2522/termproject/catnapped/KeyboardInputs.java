package ca.bcit.comp2522.termproject.catnapped;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * KeyboardInputs Class. For keyboard Inputs.
 * @author Jerry and Bryan
 * @version 2022
 */
public class KeyboardInputs implements KeyListener {

    private GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(Gamestate.state) {
            case MENU:
                gamePanel.getGame().getMenu().keyReleased(e);
                break;
            case INGAME:
                gamePanel.getGame().getInGame().keyReleased(e);
                break;
            default:
                break;
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(Gamestate.state) {
            case MENU:
                gamePanel.getGame().getMenu().keyPressed(e);
                break;
            case INGAME:
                gamePanel.getGame().getInGame().keyPressed(e);
                break;
            default:
                break;
        }

    }

}
