import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class Config {

    private final int idleThresholdSeconds;

    private final boolean audioEnabled;
    private final String audioFile;

    private final boolean imageEnabled;
    private final String imageFile;

    private final boolean videoEnabled;
    private final String videoFile;

    public Config() {
    Properties properties = new Properties();
    try(InputStream inputStream = Objects.requireNonNull(getClass().getClassLoader()
            .getResourceAsStream("config.properties"), "config.properties file not found")) {

        properties.load(inputStream);

        idleThresholdSeconds = Integer.parseInt(properties.getProperty("idle.threshold.seconds"));

        audioEnabled = Boolean.parseBoolean(properties.getProperty("reminder.audio.enabled"));
        audioFile = properties.getProperty("reminder.audio.file");

        imageEnabled = Boolean.parseBoolean(properties.getProperty("reminder.image.enabled"));
        imageFile = properties.getProperty("reminder.image.file");

        videoEnabled = Boolean.parseBoolean(properties.getProperty("reminder.video.enabled"));
        videoFile = properties.getProperty("reminder.video.file");
    }
    catch (Exception e) {
        throw new RuntimeException("Failed to load configuration", e);
    }
    }

    public int getIdleThresholdSeconds() {
        return idleThresholdSeconds;
    }

    public boolean isAudioEnabled() {
        return audioEnabled;
    }

    public String getAudioFile() {
        return audioFile;
    }

    public boolean isImageEnabled() {
        return imageEnabled;
    }

    public String getImageFile() {
        return imageFile;
    }

    public boolean isVideoEnabled() {
        return videoEnabled;
    }

    public String getVideoFile() {
        return videoFile;
    }

}
