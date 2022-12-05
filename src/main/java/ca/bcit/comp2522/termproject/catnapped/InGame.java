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
    private boolean gameOver = false;
    private GameOverScreen gameOverScreen;
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
        player = new Player(100, 100, 32, 64, this);
        player.loadLevelInfo(level1.getCurrentLevel().getLevelImage());
        pause = new Pause(this);
        gameOverScreen = new GameOverScreen(this);
    }

    public Player getPlayer() {

        return player;
    }

    @Override
    public void update() {
        if(!paused && !gameOver){
            level1.update();
            enemyManager.updateEnemies(level1.getCurrentLevel().getLevelImage(), player);
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
        }else if(gameOver) {
            gameOverScreen.drawOverlay(g);
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
                    System.out.println("F is pressed");
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

    public void resetAll() {
        //Reset everything after death
        gameOver = false;
        paused = false;
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
        if (!gameOver)
            if (paused)
                pause.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!gameOver)
            if (paused)
                pause.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(!gameOver)
            if (paused)
                pause.mouseMoved(e);

    }

    public void unpauseGame() {
        paused = false;
    }


}
