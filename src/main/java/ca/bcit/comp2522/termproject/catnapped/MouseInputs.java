package ca.bcit.comp2522.termproject.catnapped;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * MouseInputs Class. For Mouse Inputs.
 * @author Jerry and Bryan
 * @version 2022
 */
public class MouseInputs implements MouseListener, MouseMotionListener {


    private GamePanel gamePanel;

    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        System.out.println("Mouse Clicked");
        switch (Gamestate.state) {
            case INGAME:
                gamePanel.getGame().getInGame().mouseClicked(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        switch (Gamestate.state) {
            case MENU:
                gamePanel.getGame().getMenu().mousePressed(e);

                break;
            case INGAME:
                gamePanel.getGame().getInGame().mousePressed(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (Gamestate.state) {
            case MENU:
                gamePanel.getGame().getMenu().mouseReleased(e);

                break;
            case INGAME:
                gamePanel.getGame().getInGame().mouseReleased(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (Gamestate.state) {
            case MENU:
                gamePanel.getGame().getMenu().mouseMoved(e);

                break;
            case INGAME:
                gamePanel.getGame().getInGame().mouseMoved(e);
                break;
            default:
                break;

        }
    }

}