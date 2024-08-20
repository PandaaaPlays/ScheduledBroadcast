package ca.pandaaa.scheduledbroadcast.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class WeeklySchedule extends Schedule {

    private final List<String> days;
    private final List<LocalTime> times;

    public WeeklySchedule(List<String> days, List<String> times) {
        this.days = days;
        this.times = new ArrayList<>();
        for (String time : times) {
            this.times.add(LocalTime.parse(time));
        }
    }

    public boolean isOnSchedule(LocalDate dateNow, LocalTime timeNow) {
        if(this.days.stream().anyMatch(day -> day.equalsIgnoreCase(String.valueOf(dateNow.getDayOfWeek())))){
            for(LocalTime time : this.times) {
                if(timeNow.getHour() == time.getHour()
                        && timeNow.getMinute() == time.getMinute())
                    return true;
            }
        }
        return false;
    }
}
