import java.awt.*;
import java.io.File;
import java.io.IOException;

public class VideoReminder implements Reminder {

    private final String videoFile;
    public VideoReminder(String videoFile) {
        this.videoFile = videoFile;
    }

    @Override
    public void execute() {
        try {
            File video = new File(videoFile);

            Desktop.getDesktop().open(video);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {

    }
}
