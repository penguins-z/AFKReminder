package reminder;

import javax.swing.*;
import java.io.File;

public class ImageReminder implements Reminder {

    private final String imageFile;
    private JFrame frame;

    public ImageReminder(String imageFile) {
        this.imageFile = imageFile;
    }

    @Override
    public void execute() {
        frame = new JFrame("AFK Reminder");

        ImageIcon image = new ImageIcon(new File(imageFile).getAbsolutePath());

        JLabel label = new JLabel(image);

        frame.add(label);

        frame.pack();

        frame.setVisible(true);
    }

    @Override
    public void stop() {
        if (frame != null) {
            frame.dispose();
        }
    }
}