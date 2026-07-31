package config;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigValidator {

    public void validateProperties(Properties properties) {
        if(properties == null) {
            throw new NullPointerException("Properties cannot be null");
        }
        List<String> errors = new ArrayList<>();

        String[] requiredProperties = {
                "idle.threshold.seconds",
                "reminder.audio.enabled",
                "reminder.audio.file",
                "reminder.image.enabled",
                "reminder.image.file",
                "reminder.video.enabled",
                "reminder.video.file"
        };

        for (String property : requiredProperties) {
            if (properties.getProperty(property) == null) {
                errors.add("Missing Required property: " + property);
            }
        }

        if(properties.getProperty("idle.threshold.seconds") != null) {
            try {
                int idleThresholdSeconds = Integer.parseInt(
                        properties.getProperty("idle.threshold.seconds")
                );
                if(idleThresholdSeconds < 1) {
                    errors.add("idle.threshold.seconds must be greater than or equal to 1");
                }
            } catch (NumberFormatException e) {
                errors.add(
                        "idle.threshold.seconds must be a valid number"
                );
            }
        }
        String audioEnabled = properties.getProperty("reminder.audio.enabled");
        if(audioEnabled != null) {
            validateBoolean(
                    audioEnabled,
                    "reminder.audio.enabled",
                    errors
            );

            if(audioEnabled.equalsIgnoreCase("true")) {
                String audioFile = properties.getProperty("reminder.audio.file");
                if(audioFile != null) {
                    validateFile(
                            audioFile,
                            "reminder.audio.file",
                            errors
                    );
                }
            }
        }

        String imageEnabled = properties.getProperty("reminder.image.enabled");
        if(imageEnabled != null) {
            validateBoolean(
                    imageEnabled,
                    "reminder.image.enabled",
                    errors
            );
            if(imageEnabled.equalsIgnoreCase("true")) {
                String imageFile = properties.getProperty("reminder.image.file");
                if(imageFile != null) {
                    validateFile(
                            imageFile,
                            "reminder.image.file",
                            errors
                    );
                }
            }
        }

        String videoEnabled = properties.getProperty("reminder.video.enabled");
        if(videoEnabled != null) {
            validateBoolean(
                    videoEnabled,
                    "reminder.video.enabled",
                    errors
            );
            if(videoEnabled.equalsIgnoreCase("true")) {
                String videoFile = properties.getProperty("reminder.video.file");
                if(videoFile != null) {
                    validateFile(
                            videoFile,
                            "reminder.video.file",
                            errors
                    );
                }
            }
        }
        if(!errors.isEmpty()) {
            throw new IllegalArgumentException("Invalid Configuration: \n" + String.join("\n", errors));
        }
    }

    private void validateBoolean(String value, String property, List<String> errors) {
        if(value.isBlank() || (!value.equalsIgnoreCase("true")
                && !value.equalsIgnoreCase("false"))) {
            errors.add(property + " must be either true or false");
        }
    }

    private void validateFile(String fileName, String property,  List<String> errors) {
        if(fileName.isBlank()) {
            errors.add(property + " must not be blank");
            return;
        }
        try {
            if (!Files.isRegularFile(Paths.get(fileName))) {
                errors.add("File given for " + property + " does not exist or is not a file");
            }
        }
        catch (InvalidPathException e) {
            errors.add("File given for " + property + " is not a valid path");
        }
    }
}
