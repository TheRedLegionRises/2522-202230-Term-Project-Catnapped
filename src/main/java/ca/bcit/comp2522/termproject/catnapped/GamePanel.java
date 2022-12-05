package ca.bcit.comp2522.termproject.catnapped;

import javax.swing.*;
import java.awt.*;

import static ca.bcit.comp2522.termproject.catnapped.Game.GAME_WINDOW_HEIGHT;
import static ca.bcit.comp2522.termproject.catnapped.Game.GAME_WINDOW_WIDTH;

/**
 * A GamePanel class. Extends JPanel class. Creates a new frame for our game.
 */
public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;

    private Game game;

    /**
     * Constructor for our GamePanel class.
     * @param game a Game object
     */
    public GamePanel(Game game) {
        this.game = game;

        mouseInputs = new MouseInputs(this);

        setPanelSize();

        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    /**
     * Sets the size of our game frame
     */
    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WINDOW_WIDTH, GAME_WINDOW_HEIGHT); // Good arcade game size
        setPreferredSize(size); // jFrame.pack() can find this

    }

    /**
     * Renders our frame
     * @param g a Graphics object
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    /**
     * Returns the Game object in this class
     * @return a Game object.
     */
    public Game getGame() {
        return game;
    }
}
