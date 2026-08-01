package config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigManager {
    private final Path configDirectory;
    private final Path configFile;
    private final ConfigValidator configValidator;

    public ConfigManager() {
        String userHome = System.getProperty("user.home");
        configDirectory = Paths.get(userHome,"AppData", "Local", "AFKReminder");
        configFile = configDirectory.resolve("config.properties");
        configValidator = new ConfigValidator();
    }

    private Config validateAndCreateConfig(Properties properties) {
        configValidator.validateProperties(properties);
        System.out.println("afk time is : " + properties.getProperty("idle.threshold.seconds"));
        return createConfig(properties);
    }

    private Config createConfig(Properties properties) {
        return new Config(
                Integer.parseInt(properties.getProperty("idle.threshold.seconds")),
                Boolean.parseBoolean(properties.getProperty("reminder.audio.enabled")),
                properties.getProperty("reminder.audio.file"),
                Boolean.parseBoolean(properties.getProperty("reminder.image.enabled")),
                properties.getProperty("reminder.image.file"),
                Boolean.parseBoolean(properties.getProperty("reminder.video.enabled")),
                properties.getProperty("reminder.video.file")
        );
    }

    private Config createAndSaveDefaultConfig() {
        System.out.println("create called");
        try {
            Files.createDirectories(configDirectory);

            Properties properties = new Properties();

            properties.setProperty("idle.threshold.seconds", "2");

            properties.setProperty("reminder.audio.enabled", "false");
            properties.setProperty("reminder.audio.file", "");

            properties.setProperty("reminder.image.enabled", "false");
            properties.setProperty("reminder.image.file", "");

            properties.setProperty("reminder.video.enabled", "false");
            properties.setProperty("reminder.video.file", "");

            try (OutputStream outputStream = Files.newOutputStream(configFile)) {
                properties.store(
                        outputStream,
                        "AFK Reminder Configuration"
                );
            }
            return validateAndCreateConfig(properties);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to create default configuration", e
            );
        }
    }

    public Config load() {
        System.out.println("load called");
        try {
            if (Files.notExists(configFile)) {
                return createAndSaveDefaultConfig();
            }
            System.out.println("file exists so no create");
            Properties properties = new Properties();

            try (InputStream inputStream = Files.newInputStream(configFile)) {
                properties.load(inputStream);
            }
            return validateAndCreateConfig(properties);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }
}
