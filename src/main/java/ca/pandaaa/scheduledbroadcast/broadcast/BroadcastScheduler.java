package ca.pandaaa.scheduledbroadcast.broadcast;

import ca.pandaaa.scheduledbroadcast.schedule.Schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BroadcastScheduler {
    private final List<ScheduledBroadcast> broadcastList;

    public BroadcastScheduler(List<ScheduledBroadcast> broadcastList) {
        this.broadcastList = broadcastList;
    }

    public List<ScheduledBroadcast> scanForScheduledTimes() {
        LocalDate dateNow = LocalDate.now();
        LocalTime timeNow = LocalTime.now();

        List<ScheduledBroadcast> broadcastsReady = new ArrayList<>();
        for(ScheduledBroadcast broadcast : broadcastList) {
            Schedule schedule = broadcast.getSchedule();
            if(schedule.isOnSchedule(dateNow, timeNow))
                broadcastsReady.add(broadcast);
        }

        return broadcastsReady;
    }
}
