package ca.pandaaa.scheduledbroadcast.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class OneTimeSchedule extends Schedule {

    private final List<LocalDate> dates;
    private final List<LocalTime> times;

    public OneTimeSchedule(List<String> dates, List<String> times) {
        this.dates = new ArrayList<>();
        for (String date : dates) {
            this.dates.add(LocalDate.parse(date));
        }
        this.times = new ArrayList<>();
        for (String time : times) {
            this.times.add(LocalTime.parse(time));
        }
    }

    public boolean isOnSchedule(LocalDate dateNow, LocalTime timeNow) {
        boolean validDate = false;
        for(LocalDate date : dates) {
            if(dateNow.getDayOfMonth() == date.getDayOfMonth()
                    && dateNow.getMonth() == date.getMonth()
                    && dateNow.getYear() == date.getYear()) {
                validDate = true;
            }
        }
        if(!validDate)
            return false;

        for(LocalTime time : this.times) {
            if(timeNow.getHour() == time.getHour()
                    && timeNow.getMinute() == time.getMinute())
                return true;
        }
        return false;
    }
}
