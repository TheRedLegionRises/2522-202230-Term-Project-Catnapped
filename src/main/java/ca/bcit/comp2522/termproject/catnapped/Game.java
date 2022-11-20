package ca.bcit.comp2522.termproject.catnapped;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int MAX_FPS = 60;
    private final int UPS_SET = 100;

    public Game() {

        System.out.println("Game class works!");
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    //New Method for Revised Loop
    public void update() {
        gamePanel.updateGame();
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / MAX_FPS;
        double timePerUpdate = 1000000000.0 / UPS_SET; //Revised Game Loop
        int frames = 0;
//        long lastFrame = System.nanoTime();
//        long now = System.nanoTime();
        long lastCheck = System.currentTimeMillis();

        //Revised Game Loop additions
        long previousTime = System.nanoTime();
        int updates = 0;
        double deltaU = 0;
        double deltaF = 0;

        while (true) {
//            now = System.nanoTime();

            //Start of Changed stuff
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
            //End of changed stuff

            //Remove from revised loop
//            if (now - lastFrame >= timePerFrame) {
//                gamePanel.repaint();
//                lastFrame = now;
//                frames++;
//            }
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + "| UPS: " + updates); //Changed
                frames = 0;
                updates = 0;
            }
        }
    }
}