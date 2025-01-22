package domain.game1.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class AudioManager {

    // All fields are now static
    private static Clip winClip;
    private static Clip loseClip;
    private static Clip playModeMusic;
    private static Clip enterMusic;
    private static Clip[] noRuneSounds;
    private static Clip archerSound;
    private static Clip timeStealerSound;
    private static Clip gameOverMusic;
    private static Clip gameWinMusic;

    /**
     * Static block to initialize all your audio clips.
     * This runs once when the class is first loaded.
     */
    static {
        enterMusic = loadClip("music/enter.wav");
        playModeMusic = loadClip("music/playModeMusic.wav");
        gameOverMusic = loadClip("music/game_over.wav");
        gameWinMusic = loadClip("music/game_win.wav");
        // If you want to load a loseClip:
        // loseClip = loadClip("music/loseMusic.wav");
        // (If you don't have one yet, you can remove this field.)

        // Load multiple versions for "no rune" sound
        Clip noRuneSound1 = loadClip("sound/structureSound1.wav");
        Clip noRuneSound2 = loadClip("sound/structureSound2.wav");
        Clip noRuneSound3 = loadClip("sound/structureSound3.wav");
        noRuneSounds = new Clip[]{noRuneSound1, noRuneSound2, noRuneSound3};

        archerSound = loadClip("sound/archerSound.wav");
        timeStealerSound = loadClip("sound/timerAttack.wav");

        // If you have a winMusic file, load it here
        // winClip = loadClip("music/winMusic.wav");

    }

    /**
     * Private constructor to prevent instantiation of this static class.
     */
    private AudioManager() {
        // no-op
    }

    /**
     * Static helper method to load a clip from a resource path.
     */
    private static Clip loadClip(String resourcePath) {
        Clip clip = null;
        try {
            URL url = AudioManager.class.getResource(resourcePath);
            if (url == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            }
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return clip;
    }

    // -------------------------------
    // Static methods for playback
    // -------------------------------

    public static void stopMusic() {
        stopPlayModeMusic();
        stopEnterMusic();
        stopGameOverMusic();
        stopGameWinMusic();
    }

    public static void playWinMusic() {
        if (winClip != null) {
            winClip.stop();
            winClip.setFramePosition(0);
            winClip.start();
        }
    }

    public static void playPlayModeMusic() {
        if (playModeMusic != null) {
            playModeMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public static void stopPlayModeMusic() {
        if (playModeMusic != null) {
            playModeMusic.stop();
            playModeMusic.setFramePosition(0);
        }
    }

    public static void playGameOverMusic() {
        if (gameOverMusic != null) {
            gameOverMusic.stop();
            gameOverMusic.setFramePosition(0);
            gameOverMusic.start();
        }
    }

    public static void playGameWinMusic() {
        if (gameWinMusic != null) {
            gameWinMusic.stop();
            gameWinMusic.setFramePosition(0);
            gameWinMusic.start();
        }
    }

    public static void stopGameWinMusic() {
        if (gameWinMusic != null) {
            gameWinMusic.stop();
            gameWinMusic.setFramePosition(0);
        }
    }

    public static void stopGameOverMusic() {
        if (gameOverMusic != null) {
            gameOverMusic.stop();
            gameOverMusic.setFramePosition(0);
        }
    }

    public static void playEnterMusic() {
        if (enterMusic != null) {
            enterMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public static void stopEnterMusic() {
        if (enterMusic != null) {
            enterMusic.stop();
            enterMusic.setFramePosition(0);
        }
    }

    public static void playNoRuneSound() {
        if (noRuneSounds != null && noRuneSounds.length > 0) {
            int randomIndex = (int) (Math.random() * noRuneSounds.length);
            if (noRuneSounds[randomIndex] != null) {
                noRuneSounds[randomIndex].stop();
                noRuneSounds[randomIndex].setFramePosition(0);
                noRuneSounds[randomIndex].start();
            }
        }
    }

    public static void playArcherSound() {
        if (archerSound != null) {
            archerSound.stop();
            archerSound.setFramePosition(0);
            archerSound.start();
        }
    }

    public  static void playTimeStealerSound() {
        if (timeStealerSound != null) {
            timeStealerSound.stop();
            timeStealerSound.setFramePosition(0);
            timeStealerSound.start();
        }
    }

    /**
     * Sets the volume of a single Clip to a specified [0..1] range.
     */
    public static void setVolume(Clip clip, float volume) {
        if (clip == null) return;
        // Clamp volume to [0..1]
        volume = Math.max(0f, Math.min(volume, 1f));

        try {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            float min = gainControl.getMinimum(); // often around -80.0f
            float max = gainControl.getMaximum(); // often around +6.0f
            float dB = min + (max - min) * volume;
            gainControl.setValue(dB);

        } catch (IllegalArgumentException e) {
            // The clip does not support MASTER_GAIN
            System.err.println("Volume control not supported for this clip.");
        }
    }

    public static void setPlayModeVolume(int volume) {
        switch (volume) {
            case 3:
                setVolume(playModeMusic, 1.0f);
                break;
            case 2:
                setVolume(playModeMusic, 0.8f);
                break;
            case 1:
                setVolume(playModeMusic, 0.5f);
                break;
            case 0:
                setVolume(playModeMusic, 0.0f);
                break;
        }
    }

}
