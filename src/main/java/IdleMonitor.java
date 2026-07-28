import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class IdleMonitor {
    private final WinUser.LASTINPUTINFO lastInputInfo = new WinUser.LASTINPUTINFO();
    private boolean reminderTriggered = false;
    private final int idleThresholdSeconds;
    private ReminderManager reminderManager;

    public IdleMonitor(int idleThresholdSeconds, ReminderManager reminderManager) {
        this.idleThresholdSeconds = idleThresholdSeconds;
        this.reminderManager = reminderManager;
    }

    public void checkIdleTime() {
        boolean success = User32.INSTANCE.GetLastInputInfo(lastInputInfo);

        if (!success) {
            System.out.println("Failed to get last input info!");
            return;
        }

        int currentTickCount = Kernel32.INSTANCE.GetTickCount();

        int idleSeconds = (currentTickCount - lastInputInfo.dwTime) / 1000;
        //System.out.println("Idle time: " + idleSeconds + " seconds");
        if(reminderTriggered) {
            reminderManager.stopReminders();
        }
        if (idleSeconds >= idleThresholdSeconds && !reminderTriggered) {
            reminderManager.triggerReminders();
            reminderTriggered = true;
        } else if (idleSeconds < idleThresholdSeconds) {
            reminderTriggered = false;
        }
    }
}
