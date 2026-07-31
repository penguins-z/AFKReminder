package reminder;

import config.Config;

import java.util.ArrayList;
import java.util.List;

public class ReminderManager {

    private final List<Reminder> reminders = new ArrayList<>();

    public ReminderManager(Config config) {

        if(config.isAudioEnabled()) {
            reminders.add(new AudioReminder(config.getAudioFile()));
        }
        if(config.isImageEnabled()) {
            reminders.add(new ImageReminder(config.getImageFile()));
        }
        if(config.isVideoEnabled()) {
            reminders.add(new VideoReminder(config.getVideoFile()));
        }
    }

    public void triggerReminders() {
        for(Reminder reminder : reminders) {
            reminder.execute();
        }
    }

    public void stopReminders() {
        for(Reminder reminder : reminders) {
            reminder.stop();
        }
    }
}
