package ca.pandaaa.scheduledbroadcast.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class HourlySchedule extends Schedule {

    private final List<Integer> minutes;

    public HourlySchedule(List<Integer> minutes) {
        this.minutes = minutes;
    }

    public boolean isOnSchedule(LocalDate dateNow, LocalTime timeNow) {
        return minutes.contains(timeNow.getMinute());
    }
}
