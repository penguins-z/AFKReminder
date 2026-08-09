# AFK Reminder

A lightweight Windows desktop application that detects when your computer has been idle for a configurable amount of time and displays a reminder until activity resumes.

AFK Reminder is designed to help you stay aware of long periods of inactivity while studying, working, or using your computer.

## Features

* ⏱️ Configurable idle-time detection
* 🔊 Audio reminders
* 🖼️ Image reminders
* 🎥 Video reminders
* 🔁 Reminders continue until user activity is detected
* 🖱️ Detects Windows user input using the Windows API
* ⚙️ User-configurable settings
* 📦 Includes default audio, image, and video reminder media
* 🖥️ Runs from the Windows system tray
* ⏯️ Supports pause, resume and exit functionality from the Windows system tray
* 🚀 Distributed as a standalone Windows application
* 📥 Windows installer with Start Menu and Desktop shortcuts

## Download

The latest Windows installer is available in the **Releases** section of this repository.

Download:

**AFKReminder-1.0.1.exe**

Run the installer and AFK Reminder will be added to your Windows applications.

To uninstall, go to settings, search for "Add or remove programs" and uninstall from there, then go to file explorer, type %LOCALAPPDATA% in address bar and delete the AFKReminder folder there

## How It Works

AFK Reminder periodically checks the amount of time since the last user input.

When the configured idle threshold is reached, the enabled reminders are triggered.

Once the user becomes active again, the reminders stop and the idle timer resets.

The application runs in the Windows system tray while monitoring activity.

## Configuration

AFK Reminder automatically creates its configuration the first time it runs.

The configuration is stored at:

```text
%LOCALAPPDATA%\AFKReminder\config.properties
```

Example:

```properties
idle.threshold.seconds=60

reminder.audio.enabled=false
reminder.audio.file=C:/Users/YourName/AppData/Local/AFKReminder/media/afk-default-audio.wav

reminder.image.enabled=false
reminder.image.file=C:/Users/YourName/AppData/Local/AFKReminder/media/afk-default-image.png

reminder.video.enabled=true
reminder.video.file=C:/Users/YourName/AppData/Local/AFKReminder/media/afk-default-video.mp4
```

### Configuration options

| Property                 | Description                                                    |
| ------------------------ | -------------------------------------------------------------- |
| `idle.threshold.seconds` | Number of seconds of inactivity before a reminder is triggered |
| `reminder.audio.enabled` | Enables or disables the audio reminder                         |
| `reminder.audio.file`    | Path to the audio file                                         |
| `reminder.image.enabled` | Enables or disables the image reminder                         |
| `reminder.image.file`    | Path to the image file                                         |
| `reminder.video.enabled` | Enables or disables the video reminder                         |
| `reminder.video.file`    | Path to the video file                                         |

AFK Reminder validates the configuration when it starts and reports invalid or missing values rather than silently continuing with a broken configuration.

## Default Media

A default reminder package is included with the application:

```text
afk-default-audio.wav
afk-default-image.png
afk-default-video.mp4
```

The default video reminder is enabled on a fresh installation.

The media files are copied to the user's local AFK Reminder directory when the initial configuration is created.

## Technology

AFK Reminder is built with:

* **Java**
* **JavaFX** — video playback and graphical UI
* **JNA (Java Native Access)** — Windows API integration
* **Maven** — dependency and build management
* **jlink** — custom Java runtime creation
* **jpackage** — Windows application and installer packaging

## Project Structure

```text
src/
├── main/
│   ├── java/
│   │   ├── config/
│   │   ├── monitor/
│   │   ├── reminder/
│   │   └── ...
│   └── resources/
│       └── default-media/

icon/
└── AFKReminderIcon.ico

pom.xml
```

Generated build and packaging files are intentionally excluded from the repository.

## Building From Source

### Requirements

* Windows
* JDK with `jlink` and `jpackage`
* Maven
* JavaFX
* WiX Toolset (required for Windows installer creation)

Clone the repository:

```bash
git clone <repository-url>
cd idleCheck
```

Build the project:

```bash
mvn clean package
```

The resulting JAR will be generated in:

```text
target/
```

For development, the application can be run from the generated JAR or directly from the IDE.

## Releases

Stable Windows installers are distributed through GitHub Releases rather than being committed directly to the repository.

Each release contains the corresponding Windows `.exe` installer as a downloadable release asset.

## License

This project is currently provided only for personal and educational use.

---

Built as a practical Java desktop project to explore Windows API integration, JavaFX media playback, configuration management, and application packaging.
