package reminder;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;

public class VideoReminder implements Reminder {

    private final String videoFile;
    private MediaPlayer mediaPlayer;
    public VideoReminder(String videoFile) {
        this.videoFile = videoFile;
    }

    @Override
    public void execute() {
        try {
            File video = new File(videoFile);
            String videoUrl = video.toURI().toString();

            System.out.println(videoUrl);

            Media media = new Media(videoUrl);

            mediaPlayer = new MediaPlayer(media);

            MediaView mediaView = new MediaView(mediaPlayer);

            Stage stage = new Stage();

            StackPane root = new StackPane();
            root.getChildren().add(mediaView);

            Scene scene = new Scene(root);

            stage.setScene(scene);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {

    }
}
