package ca.bcit.comp2522.termproject.catnapped;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static ca.bcit.comp2522.termproject.catnapped.Constants.MenuUI.PauseButtons.SOUND_BUTTON_SIZE;

public class SoundButtons extends PauseButtons {
    private boolean muted;
    private int rowIndex, colIndex;
    private BufferedImage[][] soundImgs;
    private boolean mouseOver, mousePressed;

    public SoundButtons(int x, int y, int width, int height) {
        super(x, y, width, height);

        loadSoundImgs();
    }

    private void loadSoundImgs() {
        BufferedImage temp = LoadImages.GetImage(LoadImages.SOUND_BUTTONS);
        soundImgs = new BufferedImage[2][3];
        for (int j = 0; j < soundImgs.length; j++)
            for (int i = 0; i < soundImgs[j].length; i++)
                soundImgs[j][i] = temp.getSubimage(i * SOUND_BUTTON_SIZE, j * SOUND_BUTTON_SIZE, SOUND_BUTTON_SIZE, SOUND_BUTTON_SIZE);
    }

    public void update() {
        if (muted)
            rowIndex = 1;
        else
            rowIndex = 0;

        colIndex = 0;
        if (mouseOver)
            colIndex = 1;
        if (mousePressed)
            colIndex = 2;

    }

    public void resetBooleans() {
        mouseOver = false;
        mousePressed = false;
    }

    public void draw(Graphics g) {
        g.drawImage(soundImgs[rowIndex][colIndex], x, y, width, height, null);
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

}
