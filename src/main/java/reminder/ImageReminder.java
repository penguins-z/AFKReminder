package reminder;

import util.ScreenPosition;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ImageReminder implements Reminder {

    private final String imageFile;
    private JFrame frame;
    private final ScreenPositionEnum screenPositionEnum;
    private ScreenPosition positionUtil;

    public ImageReminder(String imageFile, ScreenPositionEnum screenPositionEnum) {
        this.imageFile = imageFile;
        this.screenPositionEnum = screenPositionEnum;
    }

    @Override
    public void execute() {

        frame = new JFrame("AFK Reminder");

        ImageIcon originalImage =
                new ImageIcon(new File(imageFile).getAbsolutePath());

        positionUtil = new ScreenPosition(
                screenPositionEnum,
                originalImage.getIconWidth(),
                originalImage.getIconHeight()
        );

        Image scaledImage =
                originalImage.getImage().getScaledInstance(
                        positionUtil.getWidth(),
                        positionUtil.getHeight(),
                        Image.SCALE_SMOOTH
                );

        ImageIcon image =
                new ImageIcon(scaledImage);

        JLabel label = new JLabel(image);

        frame.add(label);

        frame.pack();

        frame.setLocation(
                positionUtil.getX(),
                positionUtil.getY()
        );

        frame.setVisible(true);
    }

    @Override
    public void stop() {

        if (frame != null) {
            frame.dispose();
            frame = null;
        }
    }
}