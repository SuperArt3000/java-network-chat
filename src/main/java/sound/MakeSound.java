package sound;

import java.util.Objects;
import javax.sound.sampled.*;

/**
 * @author Zurbaevi Nika
 */
public class MakeSound {

    private static boolean soundOptions;

    public static synchronized void playSound(final String url) {
        if (!soundOptions) {
            new Thread(() -> {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(MakeSound.class.getClassLoader().getResource("sounds/" + url)));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }).start();
        }
    }

    public static synchronized void off() {
        soundOptions = true;
    }

    public static synchronized void on() {
        soundOptions = false;
    }

    public static boolean isIncluded() {
        return soundOptions;
    }
}
