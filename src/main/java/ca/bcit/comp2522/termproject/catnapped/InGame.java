package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class InGame extends State implements Statemethods{

    private DisplayLevel level1;
    private Player player;

    public InGame(Game game) {
        super(game);
        gameInfo();
    }

    private void gameInfo() {
        level1 = new DisplayLevel(game);
        player = new Player(100, 100, 32, 64);
        player.loadLevelInfo(level1.getCurrentLevel().getLevelImage());
    }

    public Player getPlayer() {

        return player;
    }

    @Override
    public void update() {
        level1.update();
        player.updatePlayer();

    }

    @Override
    public void draw(Graphics g) {
        level1.drawLevel(g);
        player.renderPlayer(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setMoveLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setMoveRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {

            case KeyEvent.VK_A:
                player.setMoveLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setMoveRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
