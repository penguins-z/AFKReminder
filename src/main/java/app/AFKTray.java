package app;

import java.awt.*;
import java.awt.event.ActionListener;

public class AFKTray {

    private final AFKApplication application;

    private TrayIcon trayIcon;

    public AFKTray(AFKApplication application) {
        this.application = application;
    }

    public void show() {

        if (!SystemTray.isSupported()) {

            System.out.println(
                    "System tray is not supported."
            );

            return;
        }

        try {

            SystemTray systemTray =
                    SystemTray.getSystemTray();

            Image image = Toolkit.getDefaultToolkit()
                    .getImage(
                            AFKTray.class.getResource(
                                    "/AFKReminderIcon.png"
                            )
                    );

            PopupMenu popupMenu =
                    new PopupMenu();

            MenuItem pauseItem =
                    new MenuItem("Pause");

            MenuItem resumeItem =
                    new MenuItem("Resume");

            MenuItem exitItem =
                    new MenuItem("Exit");

            pauseItem.addActionListener(
                    e -> application.pause()
            );

            resumeItem.addActionListener(
                    e -> application.resume()
            );

            exitItem.addActionListener(
                    e -> {
                        application.shutdown();
                        System.exit(0);
                    }
            );

            popupMenu.add(pauseItem);
            popupMenu.add(resumeItem);
            popupMenu.addSeparator();
            popupMenu.add(exitItem);

            trayIcon =
                    new TrayIcon(
                            image,
                            "AFK Reminder",
                            popupMenu
                    );

            trayIcon.setImageAutoSize(true);

            systemTray.add(trayIcon);

            /*trayIcon.displayMessage(
                    "AFK Reminder",
                    "Application is running.",
                    TrayIcon.MessageType.INFO
            );*/

        } catch (AWTException e) {

            e.printStackTrace();
        }
    }
}