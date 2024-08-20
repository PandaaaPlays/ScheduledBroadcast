package ca.pandaaa.scheduledbroadcast.schedule;

import org.bukkit.Bukkit;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MonthlySchedule extends Schedule {

    private final List<Integer> daysOfMonth;
    private final List<LocalTime> times;

    public MonthlySchedule(List<Integer> daysOfMonth, List<String> times) {
        this.daysOfMonth = daysOfMonth;
        this.times = new ArrayList<>();
        for (String time : times) {
            this.times.add(LocalTime.parse(time));
        }
    }

    public boolean isOnSchedule(LocalDate dateNow, LocalTime timeNow) {
        Bukkit.broadcastMessage("A " + daysOfMonth);
        Bukkit.broadcastMessage("B " + dateNow.getDayOfMonth());
        if(!daysOfMonth.contains(dateNow.getDayOfMonth()))
            return false;
        for(LocalTime time : this.times) {
            if(timeNow.getHour() == time.getHour()
                    && timeNow.getMinute() == time.getMinute())
                return true;
        }
        return false;
    }
}
