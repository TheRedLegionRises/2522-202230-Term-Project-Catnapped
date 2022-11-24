package ca.bcit.comp2522.termproject.catnapped;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static ca.bcit.comp2522.termproject.catnapped.Constants.Directions.*;

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
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer().setMoveUp(false);
                break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setMoveLeft(false);
                break;
            case KeyEvent.VK_S:
                gamePanel.getGame().getPlayer().setMoveDown(false);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setMoveRight(false);
                break;
            case KeyEvent.VK_SPACE:
                gamePanel.getGame().getPlayer().setJump(false);
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer().setMoveUp(true);
                System.out.println("W");
                break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setMoveLeft(true);
                System.out.println("A");
                break;
            case KeyEvent.VK_S:
                gamePanel.getGame().getPlayer().setMoveDown(true);
                System.out.println("S");
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setMoveRight(true);
                System.out.println("D");
                break;
            case KeyEvent.VK_SPACE:
                gamePanel.getGame().getPlayer().setJump(true);
                break;
        }
    }
}