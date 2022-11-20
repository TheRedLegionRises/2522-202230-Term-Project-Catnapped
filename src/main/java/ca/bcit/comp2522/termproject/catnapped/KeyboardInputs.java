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
            case KeyEvent.VK_A:
            case KeyEvent.VK_S:
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setMoving(false);
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer().setDirection(UP);
                System.out.println("W");
                break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setDirection(LEFT);
                System.out.println("A");
                break;
            case KeyEvent.VK_S:
                gamePanel.getGame().getPlayer().setDirection(DOWN);
                System.out.println("S");
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setDirection(RIGHT);
                System.out.println("D");
                break;
        }
    }
}