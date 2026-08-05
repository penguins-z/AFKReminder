package reminder;

import config.Config;

import java.util.ArrayList;
import java.util.List;

public class ReminderManager {

    private final List<Reminder> reminders = new ArrayList<>();

    public ReminderManager(Config config) {
        //System.out.println("Audio enabled: " + config.isAudioEnabled());
        //System.out.println("Image enabled: " + config.isImageEnabled());
        //System.out.println("Video enabled: " + config.isVideoEnabled());
        boolean audioEnabled = config.isAudioEnabled();
        boolean imageEnabled = config.isImageEnabled();
        boolean videoEnabled = config.isVideoEnabled();
        if(audioEnabled) {
            reminders.add(new AudioReminder(config.getAudioFile()));
        }
        if(imageEnabled && videoEnabled) {
            reminders.add(new VideoReminder(config.getVideoFile(), ScreenPositionEnum.RIGHT));
            reminders.add(new ImageReminder(config.getImageFile(), ScreenPositionEnum.LEFT));
        }
        else if(imageEnabled) {
            reminders.add(new ImageReminder(config.getImageFile(), ScreenPositionEnum.CENTER));
        }
        else if(videoEnabled) {
            reminders.add(new VideoReminder(config.getVideoFile(), ScreenPositionEnum.CENTER));
        }
        //System.out.println("Reminders created: " + reminders.size());
    }

    public void triggerReminders() {
        //System.out.println("Triggering " + reminders.size() + " reminders");
        for(Reminder reminder : reminders) {
            //System.out.println("Executing: " + reminder.getClass().getSimpleName());
            reminder.execute();
        }
    }

    public void stopReminders() {
        for(Reminder reminder : reminders) {
            reminder.stop();
        }
    }
}
