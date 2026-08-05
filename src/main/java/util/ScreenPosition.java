package util;

import reminder.ScreenPositionEnum;

import java.awt.*;

public class ScreenPosition {

    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public ScreenPosition(
            ScreenPositionEnum screenPositionEnum,
            int mediaWidth,
            int mediaHeight
    ) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        int availableWidth;
        int availableHeight;

        if (screenPositionEnum == ScreenPositionEnum.CENTER) {
            availableWidth = (int) (screenWidth * 0.8);
            availableHeight = (int) (screenHeight * 0.8);
        }
        else {
            availableWidth = (screenWidth / 2) - 40;
            availableHeight = (int) (screenHeight * 0.8);
        }

        double scale = Math.min(
                (double) availableWidth / mediaWidth,
                (double) availableHeight / mediaHeight
        );

        width = (int) (mediaWidth * scale);
        height = (int) (mediaHeight * scale);

        if (screenPositionEnum == ScreenPositionEnum.CENTER) {
            x = (screenWidth - width) / 2;
        }
        else if (screenPositionEnum == ScreenPositionEnum.LEFT) {
            x = (screenWidth / 2 - width) / 2;
        }
        else {
            x = screenWidth / 2
                    + (screenWidth / 2 - width) / 2;
        }

        y = (screenHeight - height) / 2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}