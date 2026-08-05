package reminder;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Objects;

public class AudioReminder implements Reminder {
    private final String audioFile;
    private Clip clip;
    public AudioReminder(String audioFile) {
        this.audioFile = audioFile;
    }
    @Override
    public void execute() {
        try {
            File file = new File(audioFile);

            try(AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(file)) {
                clip = AudioSystem.getClip();

                clip.open(audioInputStream);

                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        if(clip != null) {
            clip.stop();
            clip.close();
        }
    }
}
