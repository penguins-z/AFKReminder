package app;

import javafx.application.Platform;
import logging.AFKLogger;
import monitor.AFKMonitor;
import reminder.ReminderManager;

import java.util.concurrent.ScheduledExecutorService;

public class AFKApplication {

    private final ScheduledExecutorService executorService;
    private final ReminderManager reminderManager;
    private final AFKMonitor afkMonitor;

    private boolean shutdown = false;

    public AFKApplication(
            ScheduledExecutorService executorService,
            ReminderManager reminderManager,
            AFKMonitor afkMonitor
    ) {
        this.executorService = executorService;
        this.reminderManager = reminderManager;
        this.afkMonitor = afkMonitor;
    }

    public synchronized void pause() {

        if (shutdown) {
            return;
        }

        afkMonitor.pause();

        try {
            reminderManager.stopReminders();
        } catch (Exception e) {
            AFKLogger.error(
                    "Error while stopping reminders", e
            );
        }

        AFKLogger.info("AFK monitoring paused");
    }

    public synchronized void resume() {

        if (shutdown) {
            return;
        }

        afkMonitor.resume();

        AFKLogger.info("AFK monitoring resumed");
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