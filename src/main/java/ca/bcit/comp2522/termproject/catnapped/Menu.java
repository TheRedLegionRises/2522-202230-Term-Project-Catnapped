package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Menu Class. Controls all menu features.
 * @author Jerry and Bryan
 * @version 2022
 */

public class Menu extends State implements Statemethods{

    private MenuButtons[] buttons = new MenuButtons[3];
    private BufferedImage backgroundImg, backgroundBG;
    private int menuX, menuY, menuWidth, menuHeight;
    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
        backgroundBG = LoadImages.GetImage(LoadImages.MENU_BACKGROUND);
    }
    private void loadBackground() {
        backgroundImg = LoadImages.GetImage(LoadImages.MENU_BG);
        menuWidth = (int) (backgroundImg.getWidth());
        menuHeight = (int) (backgroundImg.getHeight());
        menuX = Game.GAME_WINDOW_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (45);

    }

    private void loadButtons() {
        buttons[0] = new MenuButtons(Game.GAME_WINDOW_WIDTH / 2, (int) (150), 0, Gamestate.INGAME);
        buttons[1] = new MenuButtons(Game.GAME_WINDOW_WIDTH / 2, (int) (220), 1, Gamestate.OPTIONS);
        buttons[2] = new MenuButtons(Game.GAME_WINDOW_WIDTH / 2, (int) (290), 2, Gamestate.QUIT);
    }

    @Override
    public void update() {
        for (MenuButtons mb : buttons)
            mb.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundBG,0 , 0 , Game.GAME_WINDOW_WIDTH, Game.GAME_WINDOW_HEIGHT, null);
        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);

        for (MenuButtons mb : buttons)
            mb.draw(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            Gamestate.state = Gamestate.INGAME;

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButtons mb : buttons){
            if(clickable(e,mb)) {
                mb.setMousePressed(true);
                break;
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButtons mb : buttons){
            if(clickable(e, mb)) {
                if(mb.isMousePressed())
                    mb.applyGamestate();
                break;
            }
        }
        resetButton();
    }

    private void resetButton() {
        for (MenuButtons mb : buttons)
            mb.resetBooleans();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButtons mb : buttons)
            mb.setMouseOver(false);

        for (MenuButtons mb : buttons){
            if(clickable(e, mb)) {
                mb.setMouseOver(true);
                break;
            }
        }
    }
}
