import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.Objects;

public class AudioReminder implements Reminder{
    private final String audioFile;
    public AudioReminder(String audioFile) {
        this.audioFile = audioFile;
    }
    @Override
    public void execute() {
            try {
                AudioInputStream audioInputStream =
                        AudioSystem.getAudioInputStream(
                                Objects.requireNonNull(getClass().getResource("/" + audioFile),
                                        "Error fetching audio... Provided audio source was not found")
                        );

                Clip clip = AudioSystem.getClip();

                clip.open(audioInputStream);

                clip.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public void stop() {

    }
}
