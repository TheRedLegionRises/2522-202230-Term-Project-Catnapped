package ca.bcit.comp2522.termproject.catnapped;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Statemethods interface. For each menu buttons.
 * @author Jerry and Bryan
 * @version 2022
 */
public interface Statemethods {
    public void update();
    public void draw(Graphics g);
    public void keyPressed(KeyEvent e);
    public void keyReleased(KeyEvent e);
    public void mouseClicked(MouseEvent e);
    public void mousePressed(MouseEvent e);
    public void mouseReleased(MouseEvent e);
    public void mouseMoved(MouseEvent e);
}
