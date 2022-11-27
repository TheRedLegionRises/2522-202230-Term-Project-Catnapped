package ca.bcit.comp2522.termproject.catnapped;

import java.awt.event.MouseEvent;

public class State {

    protected Game game;

    public State(Game game) {
        this.game = game;
    }

    public boolean clickable(MouseEvent e, MenuButtons mb) {
        return mb.getBoundaries().contains(e.getX(), e.getY());
    }

    public Game getGame() {
        return game;
    }
}
