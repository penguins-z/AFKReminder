package app;

import javafx.application.Platform;
import logging.AFKLogger;
import reminder.ReminderManager;

import java.util.concurrent.ScheduledExecutorService;

public class AFKApplication {

    private final ScheduledExecutorService executorService;
    private final ReminderManager reminderManager;

    private boolean shutdown = false;

    public AFKApplication(
            ScheduledExecutorService executorService,
            ReminderManager reminderManager
    ) {
        this.executorService = executorService;
        this.reminderManager = reminderManager;
    }

    public synchronized void shutdown() {

        if (shutdown) {
            return;
        }

        shutdown = true;

        AFKLogger.info("Application shutting down");

        try {
            reminderManager.stopReminders();
        } catch (Exception e) {
            AFKLogger.error(
                    "Error while stopping reminders",
                    e
            );
        }

        try {
            executorService.shutdownNow();
        } catch (Exception e) {
            AFKLogger.error(
                    "Error while stopping scheduler",
                    e
            );
        }

        try {
            Platform.exit();
        } catch (Exception e) {
            AFKLogger.error(
                    "Error while stopping JavaFX",
                    e
            );
        }

        AFKLogger.info("Application shutdown complete");

        AFKLogger.close();
    }
}