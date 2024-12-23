package domain.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;


public class AudioManager {

    private Clip winClip;
    private Clip loseClip;
    private Clip backgroundClip;
    private Clip enterMusic;

    public AudioManager() {
        enterMusic = loadClip("music/deneme.wav");
    }

    private Clip loadClip(String resourcePath) {
        Clip clip = null;
        try {
            URL url = getClass().getResource(resourcePath);
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return clip;
    }

    public void playWinMusic() {
        if (winClip != null) {
            winClip.stop();
            winClip.setFramePosition(0);
            winClip.start();
        }
    }

    public void playBackgroundMusic() {
        if (backgroundClip != null) {
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundClip != null) {
            backgroundClip.stop();
            backgroundClip.setFramePosition(0);
        }
    }

    public void playEnterMusic() {
        if (enterMusic != null) {
            enterMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stopEnterMusic() {
        if (enterMusic != null) {
            enterMusic.stop();
            enterMusic.setFramePosition(0);
        }
    }
}
