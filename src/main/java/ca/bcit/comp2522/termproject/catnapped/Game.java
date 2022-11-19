package ca.bcit.comp2522.termproject.catnapped;

public class Game {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    public Game() {
        System.out.println("Game class works!");
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();

    }
}
