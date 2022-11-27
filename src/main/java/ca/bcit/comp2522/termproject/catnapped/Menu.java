package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends State implements Statemethods{

    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage backgroundImg;
    private int menuX, menuY, menuWidth, menuHeight;
    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
    }
    private void loadBackground() {
        backgroundImg = LoadImages.GetImage(LoadImages.MENU_BG);
        menuWidth = (int) (backgroundImg.getWidth());
        menuHeight = (int) (backgroundImg.getHeight());
        menuX = Game.GAME_WINDOW_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (45);

    }

    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WINDOW_WIDTH / 2, (int) (150), 0, Gamestate.INGAME);
        buttons[1] = new MenuButton(Game.GAME_WINDOW_WIDTH / 2, (int) (220), 1, Gamestate.OPTIONS);
        buttons[2] = new MenuButton(Game.GAME_WINDOW_WIDTH / 2, (int) (290), 2, Gamestate.QUIT);
    }

    @Override
    public void update() {
        for (MenuButton mb : buttons)
            mb.update();
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);

        for (MenuButton mb : buttons)
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
        for (MenuButton mb : buttons){
            if(clickable(e,mb)) {
                mb.setMousePressed(true);
                break;
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton mb : buttons){
            if(clickable(e, mb)) {
                if(mb.isMousePressed())
                    mb.applyGamestate();
                break;
            }
        }
        resetButton();
    }

    private void resetButton() {
        for (MenuButton mb : buttons)
            mb.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton mb : buttons)
            mb.setMouseOver(false);

        for (MenuButton mb : buttons){
            if(clickable(e, mb)) {
                mb.setMouseOver(true);
                break;
            }
        }
    }
}
