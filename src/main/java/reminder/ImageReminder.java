package reminder;

import javax.swing.*;
import java.util.Objects;

public class ImageReminder implements Reminder {
    private final String imageFile;
    private JFrame frame;
    public ImageReminder(String imageFile) {
        this.imageFile = imageFile;
    }
    @Override
    public void execute() {
            frame = new JFrame("reminder.Reminder");

            ImageIcon image =
                    new ImageIcon(
                            Objects.requireNonNull(getClass().getResource("/" + imageFile),
                                    "Error fetching image... Provided image source was not found")
                    );

            JLabel label = new JLabel(image);

            frame.add(label);

            frame.pack();

            frame.setVisible(true);
    }

    @Override
    public void stop() {
        if(frame != null) {
            frame.dispose();
        }
    }
}
