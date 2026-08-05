import config.Config;
import config.ConfigManager;
import javafx.application.Platform;
import monitor.AFKMonitor;
import reminder.ReminderManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        Platform.startup(() -> {
            Platform.setImplicitExit(false);
        });

        ConfigManager configManager = new ConfigManager();
        Config config = configManager.load();

        ReminderManager reminderManager = new ReminderManager(config);

        AFKMonitor idleMonitor = new AFKMonitor(config.getIdleThresholdSeconds(), reminderManager);

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(idleMonitor::checkIdleTime, 0, 1, TimeUnit.SECONDS);
    }
}
