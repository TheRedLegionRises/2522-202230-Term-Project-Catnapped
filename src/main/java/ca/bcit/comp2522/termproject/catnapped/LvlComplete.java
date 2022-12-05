package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static ca.bcit.comp2522.termproject.catnapped.Constants.MenuUI.UtilButtons.UTIL_BUTTON_SIZE;

/**
 * LvlComplete helper class. Level Complete feature when completing the level.
 * @author Jerry and Bryan
 * @version 2022
 */
public class LvlComplete {
    private InGame ingame;
    private UtilButtons menu, next;
    private BufferedImage img;
    private int backgroundX, backgroundY, backgroundWidth, backgroundHeight;

    public LvlComplete(InGame ingame) {
        this.ingame = ingame;
        imageOverlay();
        imageButtons();
    }

    private void imageButtons() {
        int menuX = 330;
        int nextX = 445;
        int menuY = 195;
        next = new UtilButtons(nextX, menuY, UTIL_BUTTON_SIZE, UTIL_BUTTON_SIZE, 0);
        menu = new UtilButtons(menuX, menuY, UTIL_BUTTON_SIZE, UTIL_BUTTON_SIZE, 2);

    }

    private void imageOverlay() {
        img = LoadImages.GetImage(LoadImages.COMPLETE_OVERLAY);

        backgroundWidth =  img.getWidth();
        backgroundHeight = img.getHeight();
        backgroundX = Game.GAME_WINDOW_WIDTH / 2 - backgroundWidth / 2;
        backgroundY =  75;


    }

    public void draw(Graphics g) {
        g.drawImage(img, backgroundX,backgroundY,backgroundWidth,backgroundHeight,null);
        next.draw(g);
        menu.draw(g);

    }

    private boolean clickable(UtilButtons b, MouseEvent e) {
        return b.getBoundaries().contains(e.getX(), e.getY());

    }

    public void update() {
        next.update();
        menu.update();
    }

    public void mouseMoved(MouseEvent e){
    next.setMouseOver(false);
    menu.setMouseOver(false);

        if(clickable(menu, e))
            menu.setMouseOver(true);
        else if (clickable(next, e))
            next.setMouseOver(true);
    }

    public void mouseReleased(MouseEvent e){

        if(clickable(menu, e)) {
            if (menu.isMousePressed())
            {
                ingame.resetAll();
                Gamestate.state = Gamestate.MENU;
            }


        } else if (clickable(next, e))
            if (next.isMousePressed()) {
                ingame.LoadNextLevel();
            }
            menu.resetBooleans();
            next.resetBooleans();
    }

    public void mousePressed(MouseEvent e){

        if(clickable(menu, e))
            menu.setMousePressed(true);
        else if (clickable(next, e))
            next.setMousePressed(true);
    }

}
