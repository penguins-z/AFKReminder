package reminder;

import config.Config;

import java.util.ArrayList;
import java.util.List;

public class ReminderManager {

    private final List<Reminder> reminders = new ArrayList<>();

    public ReminderManager(Config config) {
        System.out.println("Audio enabled: " + config.isAudioEnabled());
        System.out.println("Image enabled: " + config.isImageEnabled());
        System.out.println("Video enabled: " + config.isVideoEnabled());
        if(config.isAudioEnabled()) {
            reminders.add(new AudioReminder(config.getAudioFile()));
        }
        if(config.isImageEnabled()) {
            reminders.add(new ImageReminder(config.getImageFile()));
        }
        if(config.isVideoEnabled()) {
            reminders.add(new VideoReminder(config.getVideoFile()));
        }
        System.out.println("Reminders created: " + reminders.size());
    }

    public void triggerReminders() {
        System.out.println("Triggering " + reminders.size() + " reminders");
        for(Reminder reminder : reminders) {
            System.out.println("Executing: " + reminder.getClass().getSimpleName());
            reminder.execute();
        }
    }

    public void stopReminders() {
        for(Reminder reminder : reminders) {
            reminder.stop();
        }
    }
}
