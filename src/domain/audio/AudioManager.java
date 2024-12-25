package domain.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;


public class AudioManager {

    private Clip winClip;
    private Clip loseClip;
    private Clip backgroundClip;
    private Clip enterMusic;
    private Clip[] noRuneSounds;
    private Clip archerSound;

    public AudioManager() {
        this.enterMusic = loadClip("music/enter.wav");

        Clip noRuneSound1 = loadClip("sound/structureSound1.wav");
        Clip noRuneSound2 = loadClip("sound/structureSound2.wav");
        Clip noRuneSound3 = loadClip("sound/structureSound3.wav");
        this.noRuneSounds = new Clip[]{noRuneSound1, noRuneSound2, noRuneSound3};

        this.archerSound = loadClip("sound/archerSound.wav");
    }

    private Clip loadClip(String resourcePath) {
        Clip clip = null;
        try {
            URL url = getClass().getResource(resourcePath);
            assert url != null;
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

    public void playNoRuneSound() {
        int randomIndex = (int) (Math.random() * noRuneSounds.length);
        noRuneSounds[randomIndex].stop();
        noRuneSounds[randomIndex].setFramePosition(0);
        noRuneSounds[randomIndex].start();
    }

    public void playArcherSound() {
        if (archerSound != null) {
            archerSound.stop();
            archerSound.setFramePosition(0);
            archerSound.start();
        }
    }
}
