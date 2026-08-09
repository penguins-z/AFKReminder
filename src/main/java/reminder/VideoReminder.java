package reminder;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import util.ScreenPosition;

import java.io.File;

public class VideoReminder implements Reminder {

    private final String videoFile;
    private MediaPlayer mediaPlayer;
    private Stage stage;
    private final ScreenPositionEnum screenPositionEnum;
    private ScreenPosition positionUtil;

    public VideoReminder(String videoFile, ScreenPositionEnum screenPositionEnum) {
        this.videoFile = videoFile;
        this.screenPositionEnum = screenPositionEnum;
    }

    @Override
    public void execute() {

        //System.out.println("Video execute called");

        Platform.runLater(() -> {

            try {
                File video = new File(videoFile);
                String videoUrl = video.toURI().toString();

                //System.out.println("Creating Media");

                Media media = new Media(videoUrl);

                mediaPlayer = new MediaPlayer(media);

                MediaView mediaView = new MediaView(mediaPlayer);

                mediaPlayer.setOnReady(() -> {

                    //System.out.println("Video media READY");

                    positionUtil = new ScreenPosition(
                            screenPositionEnum,
                            (int) media.getWidth(),
                            (int) media.getHeight()
                    );

                    mediaView.setFitWidth(positionUtil.getWidth());
                    mediaView.setFitHeight(positionUtil.getHeight());
                    mediaView.setPreserveRatio(true);

                    stage = new Stage();
                    stage.getIcons().add(
                            new Image(
                                    getClass().getResourceAsStream("/AFKReminderIcon.png")
                            )
                    );

                    StackPane root = new StackPane();
                    root.getChildren().add(mediaView);

                    Scene scene = new Scene(
                            root,
                            positionUtil.getWidth(),
                            positionUtil.getHeight()
                    );

                    stage.setScene(scene);

                    stage.setX(positionUtil.getX());
                    stage.setY(positionUtil.getY());

                    stage.setAlwaysOnTop(true);
                    stage.show();

                    //System.out.println("Starting video playback");
                    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                    mediaPlayer.play();
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void stop() {

        //System.out.println("Video stop called");

        Platform.runLater(() -> {

            if (mediaPlayer != null) {
                //System.out.println("Stopping video player");
                mediaPlayer.stop();
                mediaPlayer.dispose();
                mediaPlayer = null;
            }

            if (stage != null) {
                //System.out.println("Closing video stage");
                stage.close();
                stage = null;
            }
        });
    }
}