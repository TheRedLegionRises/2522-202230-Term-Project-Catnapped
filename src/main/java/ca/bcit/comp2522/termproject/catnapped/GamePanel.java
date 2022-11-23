package ca.bcit.comp2522.termproject.catnapped;

import javax.swing.*;
import java.awt.*;

import static ca.bcit.comp2522.termproject.catnapped.Game.GAME_WINDOW_HEIGHT;
import static ca.bcit.comp2522.termproject.catnapped.Game.GAME_WINDOW_WIDTH;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;

    private Game game;
    public GamePanel(Game game) {
        this.game = game;

        mouseInputs = new MouseInputs(this);

        setPanelSize();

        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WINDOW_WIDTH, GAME_WINDOW_HEIGHT); // Good arcade game size
        setPreferredSize(size); // jFrame.pack() can find this

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return game;
    }
}
