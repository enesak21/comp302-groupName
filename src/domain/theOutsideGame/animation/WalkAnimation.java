package domain.theOutsideGame.animation;

import java.awt.Image;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;

public class WalkAnimation {

    private List<String> frames;
    private int currentFrameIndex;
    private int frameCounter;  // Counter for frame updates
    private int frameDuration; // Number of updates per frame change
    private Image currentFrame;

    public WalkAnimation(String[] frames, int frameDuration) {
        this.frames = Arrays.asList(frames);
        this.frameDuration = frameDuration;  // Number of updates per frame change
        this.currentFrameIndex = 0;
        this.frameCounter = 0;  // Initialize the counter
        this.currentFrame = loadImage(frames[currentFrameIndex]);
    }

    private Image loadImage(String path) {
        return new ImageIcon(path).getImage();
    }

    public void update() {
        frameCounter++;  // Increment the counter every time update is called

        // If the counter reaches the frame duration, update the frame
        if (frameCounter >= frameDuration) {
            currentFrameIndex = (currentFrameIndex + 1) % frames.size();  // Loop through frames
            currentFrame = loadImage(frames.get(currentFrameIndex));
            frameCounter = 0;  // Reset the counter after frame change
        }
    }

    public Image getCurrentFrame() {
        return currentFrame;
    }
}
