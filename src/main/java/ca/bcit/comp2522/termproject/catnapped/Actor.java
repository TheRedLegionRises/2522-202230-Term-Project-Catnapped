package ca.bcit.comp2522.termproject.catnapped;

public class Actor {

    private int maxHealth = 50;

    private int height = 16;

    private float x;

    private float y;

    private int width = 32;

    private int currentHealth;

    private int state;

    public Actor(int maxHealth) {
        this.maxHealth = maxHealth;
    }
}
