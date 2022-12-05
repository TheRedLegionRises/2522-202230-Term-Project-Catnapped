package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Ingame Class. Runs our game engine.
 * @author Jerry and Bryan
 * @version 2022
 */
public class InGame extends State implements Statemethods{
    private Pause pause;
    private DisplayLevel displayLevel;
    private Player player;
    private AllEnemiesManager enemyManager;
    private boolean paused = false;
    private boolean gameOver = false;
    private GameOverScreen gameOverScreen;
    private LvlComplete LvlComplete;
    private int xLvlOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WINDOW_WIDTH);
    private int rightBorder = (int) (0.9 * Game.GAME_WINDOW_WIDTH);
    private int maxLvlOffsetX;
    private boolean lvlcompleted;

    /**
     * InGame Constructor
     */
    public InGame(Game game) {
        super(game);
        gameInfo();

        calcOffSet();
        LoadStartLevel();
    }
    /**
     * Loads next level
     */
    public void LoadNextLevel() {
        resetAll();
        displayLevel.loadNextLevel();
        player.setSpawn(displayLevel.getCurrentLevel().getPlayerSpawn());
    }

    /**
     * Loads level one
     */
    private void LoadStartLevel() {
        enemyManager.LoadEnemies(displayLevel.getCurrentLevel());
    }
    /**
     * Calculates the OffSet for scrolling - WOIP
     */
    private void calcOffSet() {
        maxLvlOffsetX = displayLevel.getCurrentLevel().getLvlOffset();
    }

    private void gameInfo() {
        displayLevel = new DisplayLevel(game);
        enemyManager = new AllEnemiesManager(this);

        player = new Player(100, 100, 32, 64, this);
        player.loadLevelInfo(displayLevel.getCurrentLevel().getLevelImage());
        player.setSpawn(displayLevel.getCurrentLevel().getPlayerSpawn());

        pause = new Pause(this);
        gameOverScreen = new GameOverScreen(this);
        LvlComplete = new LvlComplete(this);
    }

    public Player getPlayer() {

        return player;
    }

    @Override
    public void update() {
        if(paused) {
            pause.update();
        }
        else if (lvlcompleted) {
            LvlComplete.update();
        }
        else if(!gameOver){
            displayLevel.update();
            enemyManager.updateEnemies(displayLevel.getCurrentLevel().getLevelImage(), player);
            player.updatePlayer();
            checkCloseToBorder();
        }
    }
    /**
     * Checks if player is close to a border of game window - WOIP.
     */
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
        displayLevel.drawLevel(g, xLvlOffset);
        player.renderPlayer(g, xLvlOffset);
        enemyManager.renderEnemies(g, xLvlOffset);
        if(paused) {
                g.setColor(new Color(0,0,0,150));
                g.fillRect(0,0,Game.GAME_WINDOW_WIDTH, Game.GAME_WINDOW_HEIGHT);
            pause.draw(g);
        }else if(gameOver) {
            gameOverScreen.drawOverlay(g);
        }
        else if (lvlcompleted) {
            LvlComplete.draw(g);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(gameOver) {
            gameOverScreen.keyPressed(e);
        } else {
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
                case KeyEvent.VK_F:
//                    System.out.println("F is pressed");
                    if (!player.getFirstAttackReset()) {
                        player.setFirstAttackAnimationReset(true);
                    }
                    player.setAttacking(true);
                    break;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!gameOver) {
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
                case KeyEvent.VK_F:
//                player.setAttacking(true);
            }
        }

    }
// Reset booleans and values in methods and classes.
    public void resetAll() {
        //Reset everything after death
        gameOver = false;
        paused = false;
        lvlcompleted = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
    }

    public void setGameOver(boolean isGameOver) {
        this.gameOver = isGameOver;
    }

    public void checkPlayerHitEnemy() {
        enemyManager.checkPlayerHitsEnemy(player.getAttackBox());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//attacking

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pause.mousePressed(e);
            else if (lvlcompleted)
                LvlComplete.mousePressed(e);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pause.mouseReleased(e);
            else if (lvlcompleted)
                LvlComplete.mouseReleased(e);
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pause.mouseMoved(e);
            else if (lvlcompleted)
                LvlComplete.mouseMoved(e);
        }
    }
// Unpauses game
    public void unpauseGame() {
        paused = false;
    }
    public AllEnemiesManager getEnemyManager() {
        return enemyManager;
    }
    public void setMaxLvlOffsetX(int lvlOffset) {
        this.maxLvlOffsetX = lvlOffset;
    }
    public void setLevelComplete(boolean lvlcompleted) {
        this.lvlcompleted = lvlcompleted;
    }
}
