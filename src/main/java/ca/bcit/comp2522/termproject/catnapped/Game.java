package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;

/**
 * Game class. Implements Runnable class.
 * @author jerry and bryan
 * @version 2022
 */
public class Game implements Runnable {

    private final GameWindow gameWindow;
    private final GamePanel gamePanel;
    private Thread gameThread;
    private final int MAX_FPS = 120;
    private final int UPS_SET = 200;
    private InGame inGame;
    private Menu menu;
    private DisplayLevel level1;
    private AllEnemiesManager enemyManager;
    private Player player;
    
    public final static int DEFAULT_TILE_SIZE = 32;
    public static final int TILES_IN_WIDTH = 26;
    public static final int TILES_IN_HEIGHT = 14;
    public static final int GAME_WINDOW_WIDTH = TILES_IN_WIDTH * DEFAULT_TILE_SIZE;
    public static final int GAME_WINDOW_HEIGHT = TILES_IN_HEIGHT * DEFAULT_TILE_SIZE;

    /**
     * Constructor for our Game class.
     * @author jerry and bryan
     * @version 2022
     */
    public Game() {
        gameInfo();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    /**
     * Initializes all necessary elements of our game.
     */
    private void gameInfo() {
        menu = new Menu(this);
        inGame = new InGame(this);
        level1 = new DisplayLevel(this);
//        enemyManager = new AllEnemiesManager();
        player = new Player(100, 100, 32, 128, inGame);
        player.loadLevelInfo(level1.getCurrentLevel().getLevelImage());

    }

    /**
     * Starts the game loop
     */
    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Updates the current game state and changes what is displayed accordingly.
     */
    public void update() {
        switch(Gamestate.state) {
            case MENU:
                    menu.update();
                break;
            case INGAME:
                inGame.update();
                break;
            case OPTIONS:
                break;
            case QUIT:
                System.exit(0);
                break;
            default:
                break;


        }
    }

    /**
     * Renders the current game state onto the screen
     * @param g Graphics object
     */
    public void render(Graphics g) {

        switch(Gamestate.state) {
            case MENU:
            menu.draw(g);
                break;
            case INGAME:
                inGame.draw(g);
                break;
            default:
                break;
        }
    }

    /**
     * Method for running the game.
     */
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

    /**
     * Returns the Player object that is initialized in this class.
     * @return Player object
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the Menu object initialized inside this current class.
     * @return a Menu object
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     * Returns the InGame object initialized inside this current class.
     * @return
     */
    public InGame getInGame(){
        return inGame;
    }

}
