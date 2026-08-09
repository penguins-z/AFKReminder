package config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigManager {

    private final Path configDirectory;
    private final Path configFile;
    private final Path mediaDirectory;

    private final ConfigValidator configValidator;

    public ConfigManager() {

        String userHome = System.getProperty("user.home");

        configDirectory = Paths.get(
                userHome,
                "AppData",
                "Local",
                "AFKReminder"
        );

        configFile = configDirectory.resolve("config.properties");

        mediaDirectory = configDirectory.resolve("media");

        configValidator = new ConfigValidator();
    }

    private Config validateAndCreateConfig(Properties properties) {

        configValidator.validateProperties(properties);

        return createConfig(properties);
    }

    private Config createConfig(Properties properties) {

        return new Config(
                Integer.parseInt(
                        properties.getProperty("idle.threshold.seconds")
                ),

                Boolean.parseBoolean(
                        properties.getProperty("reminder.audio.enabled")
                ),

                properties.getProperty("reminder.audio.file"),

                Boolean.parseBoolean(
                        properties.getProperty("reminder.image.enabled")
                ),

                properties.getProperty("reminder.image.file"),

                Boolean.parseBoolean(
                        properties.getProperty("reminder.video.enabled")
                ),

                properties.getProperty("reminder.video.file")
        );
    }

    private Config createAndSaveDefaultConfig() {

        try {

            Files.createDirectories(configDirectory);
            Files.createDirectories(mediaDirectory);

            Path audioFile = copyDefaultMedia(
                    "afk-default-audio.wav"
            );

            Path imageFile = copyDefaultMedia(
                    "afk-default-image.png"
            );

            Path videoFile = copyDefaultMedia(
                    "afk-default-video.mp4"
            );

            Properties properties = new Properties();

            properties.setProperty(
                    "idle.threshold.seconds",
                    "120"
            );

            properties.setProperty(
                    "reminder.audio.enabled",
                    "false"
            );

            properties.setProperty(
                    "reminder.audio.file",
                    audioFile.toString()
            );

            properties.setProperty(
                    "reminder.image.enabled",
                    "false"
            );

            properties.setProperty(
                    "reminder.image.file",
                    imageFile.toString()
            );

            properties.setProperty(
                    "reminder.video.enabled",
                    "true"
            );

            properties.setProperty(
                    "reminder.video.file",
                    videoFile.toString()
            );

            try (OutputStream outputStream =
                         Files.newOutputStream(configFile)) {

                properties.store(
                        outputStream,
                        "AFK Reminder Configuration"
                );
            }

            return validateAndCreateConfig(properties);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to create default configuration",
                    e
            );
        }
    }

    private Path copyDefaultMedia(String fileName)
            throws IOException {

        Path destination =
                mediaDirectory.resolve(fileName);

        if (Files.notExists(destination)) {

            String resourcePath =
                    "/default-media/" + fileName;

            try (InputStream inputStream =
                         ConfigManager.class
                                 .getResourceAsStream(resourcePath)) {

                if (inputStream == null) {

                    throw new IOException(
                            "Default media resource not found: "
                                    + resourcePath
                    );
                }

                Files.copy(
                        inputStream,
                        destination
                );
            }
        }

        return destination;
    }

    public Config load() {

        try {

            if (Files.notExists(configFile)) {
                return createAndSaveDefaultConfig();
            }

            String content = Files.readString(configFile);
            content = content.replace("\"", "");
            content = content
                    .replace("\\\\", "/")
                    .replace("\\:", ":")
                    .replace("\\", "/");

            Properties properties = new Properties();

            try (StringReader reader = new StringReader(content)) {
                properties.load(reader);
            }

            return validateAndCreateConfig(properties);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to load configuration",
                    e
            );
        }
    }
}