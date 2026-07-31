package config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigManager {
    private Path configDirectory;
    private Path configFile;

    public ConfigManager() {
        String userName = System.getProperty("user.home");
        configDirectory = Paths.get(userName,"AppData", "Local", "AFKReminder");
        configFile = configDirectory.resolve("config.properties");
    }

    private Config createDefaultConfig() {
        try {
            Files.createDirectories(configDirectory);

            Properties properties = new Properties();

            properties.setProperty("idle.threshold.seconds", "300");

            properties.setProperty("reminder.audio.enabled", "false");
            properties.setProperty("reminder.audio.file", "");

            properties.setProperty("reminder.image.enabled", "false");
            properties.setProperty("reminder.image.file", "");

            properties.setProperty("reminder.video.enabled", "false");
            properties.setProperty("reminder.video.file", "");

            try (OutputStream outputStream = Files.newOutputStream(configFile)) {
                properties.store(
                        outputStream,
                        "AFK reminder.Reminder Configuration"
                );
            }

            return new Config(
                    300,
                    false,
                    "",
                    false,
                    "",
                    false,
                    ""
            );

        } catch (IOException e) {
            throw new RuntimeException("Failed to create default configuration", e);
        }
    }

    public Config load() {

        int idleThresholdSeconds;

        boolean audioEnabled;
        String audioFile;

        boolean imageEnabled;
        String imageFile;

        boolean videoEnabled;
        String videoFile;

        try {
            if (Files.notExists(configFile)) {
                return createDefaultConfig();
            }

            Properties properties = new Properties();

            try (InputStream inputStream = Files.newInputStream(configFile)) {
                properties.load(inputStream);
            }

            new ConfigValidator().validateProperties(properties);

            return new Config(
                    idleThresholdSeconds = Integer.parseInt(properties.getProperty("idle.threshold.seconds")),

                    audioEnabled = Boolean.parseBoolean(
                            properties.getProperty("reminder.audio.enabled")
                    ),
                    audioFile = properties.getProperty("reminder.audio.file"),

                    imageEnabled = Boolean.parseBoolean(
                            properties.getProperty("reminder.image.enabled")
                    ),
                    imageFile = properties.getProperty("reminder.image.file"),

                    videoEnabled = Boolean.parseBoolean(
                            properties.getProperty("reminder.video.enabled")
                    ),
                    videoFile = properties.getProperty("reminder.video.file")
            );

        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }
}
