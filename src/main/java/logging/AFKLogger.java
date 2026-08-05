package logging;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AFKLogger {

    private static final Path LOG_DIRECTORY = Paths.get(
            System.getProperty("user.home"),
            "AppData",
            "Local",
            "AFKReminder"
    );

    private static final Path LOG_FILE =
            LOG_DIRECTORY.resolve("app.log");

    private static Logger logger;

    private AFKLogger() {
        // Prevent instantiation.
    }

    public static void initialize() {
        try {
            Files.createDirectories(LOG_DIRECTORY);

            // Clear the previous session's log.
            Files.writeString(LOG_FILE, "");

            logger = Logger.getLogger("AFKReminder");
            logger.setUseParentHandlers(false);

            FileHandler fileHandler = new FileHandler(
                    LOG_FILE.toString(),
                    true
            );

            fileHandler.setFormatter(new SimpleFormatter());

            logger.addHandler(fileHandler);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void info(String message) {
        if (logger != null) {
            logger.info(message);
        }
    }

    public static void error(String message, Throwable throwable) {
        if (logger != null) {
            logger.log(Level.SEVERE, message, throwable);
        }
    }
}