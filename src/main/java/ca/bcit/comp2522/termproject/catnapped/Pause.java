package ca.bcit.comp2522.termproject.catnapped;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static ca.bcit.comp2522.termproject.catnapped.Constants.MenuUI.PauseButtons.*;
import static ca.bcit.comp2522.termproject.catnapped.Constants.MenuUI.UtilButtons.*;
import static ca.bcit.comp2522.termproject.catnapped.Constants.MenuUI.VolumeButtons.*;

public class Pause {

    private InGame inGame;
    private VolumeButtons volumeButtons;
    private BufferedImage backgroundImage;
    private SoundButtons musicButton, sfxButton;
    private UtilButtons menuButton, replayButton, unpauseButton;
    private int backgroundX,backgroundY,backgroundWidth, backgroundHeight;

    public Pause(InGame inGame) {

        this.inGame = inGame;
        loadBackground();
        createSoundButtons();
        createUtilButtons();
        createVolumeButton();

    }

    private void loadBackground() {
        backgroundImage = LoadImages.GetImage(LoadImages.PAUSE_MENU);
        backgroundWidth = backgroundImage.getWidth();
        backgroundHeight = backgroundImage.getHeight();
        backgroundX = Game.GAME_WINDOW_WIDTH / 2 - backgroundWidth / 2;
        backgroundY = 25;

    }
    private void createVolumeButton() {
        int vX = 309; // Magic Number
        int vY = 278; // Magic Number
        volumeButtons = new VolumeButtons(vX, vY, SLIDER_WIDTH, VOLUME_BUTTON_HEIGHT);
    }

    private void createUtilButtons() {
        int menuX = 313; // Magic Number
        int replayX = 387 ; // Magic Number
        int unpauseX =462; // Magic Number
        int bY = 325; // Magic Number

        menuButton = new UtilButtons(menuX, bY, UTIL_BUTTON_SIZE, UTIL_BUTTON_SIZE, 2);
        replayButton = new UtilButtons(replayX, bY,UTIL_BUTTON_SIZE ,UTIL_BUTTON_SIZE , 1);
        unpauseButton = new UtilButtons(unpauseX, bY,UTIL_BUTTON_SIZE , UTIL_BUTTON_SIZE, 0);

    }

    private void createSoundButtons() {
        int soundX = 450; // Magic Number
        int musicY = 140; // Magic Number
        int sfxY = 186; // Magic Number
        musicButton = new SoundButtons(soundX, musicY, SOUND_BUTTON_SIZE, SOUND_BUTTON_SIZE);
        sfxButton = new SoundButtons(soundX, sfxY, SOUND_BUTTON_SIZE, SOUND_BUTTON_SIZE);

    }

    public void update() {

        musicButton.update();
        sfxButton.update();
        menuButton.update();
        replayButton.update();
        unpauseButton.update();
        volumeButtons.update();

    }


    public void draw(Graphics g) {
        // Background
        g.drawImage(backgroundImage, backgroundX, backgroundY, backgroundWidth, backgroundHeight, null);

        // Sound Buttons
        musicButton.draw(g);
        sfxButton.draw(g);

        // Util Buttons
        menuButton.draw(g);
        replayButton.draw(g);
        unpauseButton.draw(g);

        // Volume Buttons
        volumeButtons.draw(g);
    }

    private boolean clickable(MouseEvent e, PauseButtons b) {
        return b.getBoundaries().contains(e.getX(), e.getY());
    }

    public void mouseDragged(MouseEvent e) {
        if (volumeButtons.isMousePressed()) {
            volumeButtons.changeX(e.getX());
        }

    }

    public void mousePressed(MouseEvent e) {
        if (clickable(e, musicButton))
            musicButton.setMousePressed(true);
        else if (clickable(e, sfxButton))
            sfxButton.setMousePressed(true);
        else if (clickable(e, menuButton))
            menuButton.setMousePressed(true);
        else if (clickable(e, replayButton))
            replayButton.setMousePressed(true);
        else if (clickable(e, unpauseButton))
            unpauseButton.setMousePressed(true);
        else if (clickable(e, volumeButtons))
            volumeButtons.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (clickable(e, musicButton)) {
            if (musicButton.isMousePressed())
                musicButton.setMuted(!musicButton.isMuted());

        } else if (clickable(e, sfxButton)) {
            if (sfxButton.isMousePressed())
                sfxButton.setMuted(!sfxButton.isMuted());
        } else if (clickable(e, menuButton)) {
            if (menuButton.isMousePressed()) {
                Gamestate.state = Gamestate.MENU;
                inGame.unpauseGame();
            }
        } else if (clickable(e, replayButton)) {
            if (replayButton.isMousePressed())
                System.out.println("No function, but replays level"); //WOIP
        } else if (clickable(e, unpauseButton)) {
            if (unpauseButton.isMousePressed())
                inGame.unpauseGame();
        }

        musicButton.resetBooleans();
        sfxButton.resetBooleans();
        menuButton.resetBooleans();
        replayButton.resetBooleans();
        unpauseButton.resetBooleans();
        volumeButtons.resetBooleans();

    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        menuButton.setMouseOver(false);
        replayButton.setMouseOver(false);
        unpauseButton.setMouseOver(false);
        volumeButtons.setMouseOver(false);

        if (clickable(e, musicButton))
            musicButton.setMouseOver(true);
        else if (clickable(e, sfxButton))
            sfxButton.setMouseOver(true);
        else if (clickable(e, menuButton))
            menuButton.setMouseOver(true);
        else if (clickable(e, replayButton))
            replayButton.setMouseOver(true);
        else if (clickable(e, unpauseButton))
            unpauseButton.setMouseOver(true);
        else if (clickable(e, volumeButtons))
            volumeButtons.setMouseOver(true);

    }

}
