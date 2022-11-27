package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int MAX_FPS = 120;
    private final int UPS_SET = 200;
    private DisplayLevel level1;
    private Player player;

    public final static int DEFAULT_TILE_SIZE = 32;
    public static final int TILES_IN_WIDTH = 26;
    public static final int TILES_IN_HEIGHT = 14;
    public static final int GAME_WINDOW_WIDTH = TILES_IN_WIDTH * DEFAULT_TILE_SIZE;
    public static final int GAME_WINDOW_HEIGHT = TILES_IN_HEIGHT * DEFAULT_TILE_SIZE;

    public Game() {

        gameInfo();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void gameInfo() {
        level1 = new DisplayLevel(this);
        player = new Player(100, 100, 32, 64);
        player.loadLevelInfo(level1.getCurrentLevel().getLevelImage());
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    //New Method for Revised Loop
    public void update() {
        player.updatePlayer();
        level1.update();
    }

    public void render(Graphics g) {
        level1.drawLevel(g);
        player.renderPlayer(g);
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / MAX_FPS;
        double timePerUpdate = 1000000000.0 / UPS_SET;
        int frames = 0;

        long lastCheck = System.currentTimeMillis();

        long previousTime = System.nanoTime();
        int updates = 0;
        double deltaU = 0;
        double deltaF = 0;

        while (true) {

            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();

                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + "| UPS: " + updates); //Changed
                frames = 0;
                updates = 0;
            }
        }
    }

    public Player getPlayer() {
        return player;
    }
}