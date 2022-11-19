package ca.bcit.comp2522.termproject.catnapped;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {

    private GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
//        System.out.println("test");     Test for Key presses
    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch(e.getKeyCode()) {
            case KeyEvent.VK_W:
//                System.out.println("W");
                break;
            case KeyEvent.VK_A:
//                System.out.println("A");
                break;
            case KeyEvent.VK_S:
//                System.out.println("S");
                break;
            case KeyEvent.VK_D:
//                System.out.println("D");
                break;

        }
    }
}
