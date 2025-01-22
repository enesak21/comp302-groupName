package domain.theOutsideGame.animation;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Animation {

    protected Image[] frames;
    protected int currentFrameIndex = 0;
    protected long lastTime;
    protected long delay;
    protected boolean isPlaying = true;

    public Animation(String[] framePaths, long delay) {
        this.delay = delay;
        loadFrames(framePaths);
    }

    protected void loadFrames(String[] framePaths) {
        frames = new Image[framePaths.length];
        for (int i = 0; i < framePaths.length; i++) {
            frames[i] = new ImageIcon(framePaths[i]).getImage();
        }
    }

    public Image getCurrentFrame() {
        return frames[currentFrameIndex];
    }

    public void update() {
        if (isPlaying && System.currentTimeMillis() - lastTime >= delay) {
            currentFrameIndex = (currentFrameIndex + 1) % frames.length;
            lastTime = System.currentTimeMillis();
        }
    }

    public void reset() {
        currentFrameIndex = 0;
        lastTime = System.currentTimeMillis();
    }

    public void stop() {
        isPlaying = false;
    }

    public void play() {
        isPlaying = true;
    }
}
