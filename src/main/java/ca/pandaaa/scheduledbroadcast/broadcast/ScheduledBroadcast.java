package ca.pandaaa.scheduledbroadcast.broadcast;

import ca.pandaaa.automaticbroadcast.broadcast.Broadcast;
import ca.pandaaa.scheduledbroadcast.schedule.Schedule;
import org.bukkit.Sound;

import java.util.List;

public class ScheduledBroadcast extends Broadcast {

    private final Schedule schedule;

    public ScheduledBroadcast(String title, List<String> messages, Sound sound, List<String> hoverMessages, String clickMessage, List<String> exemptedPlayers, List<String> consoleCommands, Schedule schedule) {
        super(title, messages, sound, hoverMessages, clickMessage, exemptedPlayers, consoleCommands);
        this.schedule = schedule;
    }

    public Schedule getSchedule() {
        return this.schedule;
    }

    public Broadcast getConvertedBroadcast() {
        return new Broadcast(
                this.getTitle(),
                this.getMessages(),
                this.getSound(),
                this.getHoverMessages(),
                this.getClickMessage(),
                this.getExemptedPlayers(),
                this.getConsoleCommands()
        );
    }
}
