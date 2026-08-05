import app.AFKApplication;
import app.AFKTray;
import config.Config;
import config.ConfigManager;
import javafx.application.Platform;
import logging.AFKLogger;
import monitor.AFKMonitor;
import reminder.ReminderManager;

import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        AFKLogger.initialize();

        AFKLogger.info("Application starting");

        ScheduledExecutorService executorService = null;

        try {

            Platform.startup(() -> {
                Platform.setImplicitExit(false);
            });

            ConfigManager configManager =
                    new ConfigManager();

            Config config =
                    configManager.load();

            ReminderManager reminderManager =
                    new ReminderManager(config);

            AFKMonitor afkMonitor =
                    new AFKMonitor(
                            config.getIdleThresholdSeconds(),
                            reminderManager
                    );

            executorService =
                    Executors.newSingleThreadScheduledExecutor();

            executorService.scheduleAtFixedRate(
                    afkMonitor::checkIdleTime,
                    0,
                    1,
                    TimeUnit.SECONDS
            );

            AFKApplication application =
                    new AFKApplication(
                            executorService,
                            reminderManager,
                            afkMonitor
                    );

            AFKTray tray =
                    new AFKTray(application);

            tray.show();

            Runtime.getRuntime().addShutdownHook(
                    new Thread(
                            application::shutdown,
                            "AFK-Shutdown-Hook"
                    )
            );

            AFKLogger.info(
                    "Application started successfully"
            );

        } catch (Exception e) {

            AFKLogger.error(
                    "Application failed to start",
                    e
            );

            showErrorDialog(e);

            if (executorService != null) {
                executorService.shutdownNow();
            }

            Platform.exit();

            AFKLogger.close();

            System.exit(1);
        }
    }

    private static void showErrorDialog(Exception e) {

        String message = e.getMessage();

        if (message == null || message.isBlank()) {
            message = "An unexpected error occurred.";
        }

        JOptionPane.showMessageDialog(
                null,
                message,
                "AFK Reminder - Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}