package reminder;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import util.ScreenPosition;

public class ImageReminder implements Reminder {

    private final String imageFile;
    private final ScreenPositionEnum screenPositionEnum;

    private Stage stage;

    public ImageReminder(
            String imageFile,
            ScreenPositionEnum screenPositionEnum
    ) {
        this.imageFile = imageFile;
        this.screenPositionEnum = screenPositionEnum;
    }

    @Override
    public void execute() {

        System.out.println("Image execute called");

        Platform.runLater(() -> {

            try {

                Image image = new Image(
                        "file:" + imageFile
                );

                int mediaWidth = (int) image.getWidth();
                int mediaHeight = (int) image.getHeight();

                System.out.println(
                        "Image dimensions: "
                                + mediaWidth
                                + "x"
                                + mediaHeight
                );

                ScreenPosition positionUtil =
                        new ScreenPosition(
                                screenPositionEnum,
                                mediaWidth,
                                mediaHeight
                        );

                ImageView imageView = new ImageView(image);

                imageView.setFitWidth(positionUtil.getWidth());
                imageView.setFitHeight(positionUtil.getHeight());
                imageView.setPreserveRatio(true);

                StackPane root = new StackPane();
                root.getChildren().add(imageView);

                Scene scene = new Scene(
                        root,
                        positionUtil.getWidth(),
                        positionUtil.getHeight()
                );

                stage = new Stage();

                stage.setScene(scene);

                stage.setX(positionUtil.getX());
                stage.setY(positionUtil.getY());

                stage.setAlwaysOnTop(true);
                stage.show();

                System.out.println("Image displayed");

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void stop() {

        System.out.println("Image stop called");

        Platform.runLater(() -> {

            if (stage != null) {

                System.out.println("Closing image stage");

                stage.close();
                stage = null;
            }
        });
    }
}