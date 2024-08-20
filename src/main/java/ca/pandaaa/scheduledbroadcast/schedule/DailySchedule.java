package ca.pandaaa.scheduledbroadcast.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DailySchedule extends Schedule {

    private final List<LocalTime> times;

    public DailySchedule(List<String> times) {
        this.times = new ArrayList<>();
        for (String time : times) {
            this.times.add(LocalTime.parse(time));
        }
    }

    public boolean isOnSchedule(LocalDate dateNow, LocalTime timeNow) {
        for(LocalTime time : this.times) {
            if(timeNow.getHour() == time.getHour()
                    && timeNow.getMinute() == time.getMinute())
                return true;
        }
        return false;
    }
}
