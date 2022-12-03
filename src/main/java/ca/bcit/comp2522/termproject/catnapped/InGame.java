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
    private int xLvlOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WINDOW_WIDTH);
    private int rightBorder = (int) (0.9 * Game.GAME_WINDOW_WIDTH);
    private int lvlTilesWide = LoadImages.GetLevelImages()[0].length;
    private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
    private int maxLvlOffsetX = maxTilesOffset * Game.TEST_SIZE;

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
            checkCloseToBorder();

        } else {
            pause.update();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder)
            xLvlOffset += diff - rightBorder;
        else if (diff < leftBorder)
            xLvlOffset += diff - leftBorder;

        if (xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if (xLvlOffset < 0)
            xLvlOffset = 0;

    }

    @Override
    public void draw(Graphics g) {
        level1.drawLevel(g, xLvlOffset);
        player.renderPlayer(g, xLvlOffset);
        enemyManager.renderEnemies(g, xLvlOffset);
        if(paused) {
                g.setColor(new Color(0,0,0,150));
                g.fillRect(0,0,Game.GAME_WINDOW_WIDTH, Game.GAME_WINDOW_HEIGHT);
            pause.draw(g);
        }
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