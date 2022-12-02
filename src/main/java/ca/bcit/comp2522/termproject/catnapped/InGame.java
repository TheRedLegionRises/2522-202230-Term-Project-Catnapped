package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class InGame extends State implements Statemethods{

    private Pause pause;
    private DisplayLevel level1;
    private Player player;

    private AllEnemiesManager enemyManager;
    private boolean paused = false;

    public InGame(Game game) {
        super(game);
        gameInfo();
    }

    private void gameInfo() {
        level1 = new DisplayLevel(game);
        enemyManager = new AllEnemiesManager(this);
        player = new Player(100, 100, 32, 64);
        player.loadLevelInfo(level1.getCurrentLevel().getLevelImage());
        pause = new Pause(this);
    }

    public Player getPlayer() {

        return player;
    }

    @Override
    public void update() {
        if(!paused){
            level1.update();
            enemyManager.updateEnemies(level1.getCurrentLevel().getLevelImage());
            player.updatePlayer();
        } else {
            pause.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        level1.drawLevel(g);
        player.renderPlayer(g);
        enemyManager.renderEnemies(g);
        if(paused)
        pause.draw(g);
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
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
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
//attacking

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused)
            pause.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused)
            pause.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused)
            pause.mouseMoved(e);

    }

    public void unpauseGame() {
        paused = false;
    }


}
