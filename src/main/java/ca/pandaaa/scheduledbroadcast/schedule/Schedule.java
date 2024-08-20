package ca.pandaaa.scheduledbroadcast.schedule;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Schedule {

    public abstract boolean isOnSchedule(LocalDate dateNow, LocalTime timeNow);
}
