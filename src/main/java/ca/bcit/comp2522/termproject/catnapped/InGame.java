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
        } else {
            pause.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        level1.drawLevel(g);
        player.renderPlayer(g);
        enemyManager.renderEnemies(g);
        if(paused) {
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
