package schedule;

import exceptions.IncompatibleScheduleException;

public class Schedule {

    private Day day;
    private int hour;

    public Schedule(Day day, int hour) {
        if(hour < 9 || hour > 19) throw new IncompatibleScheduleException();
        this.day = day ;
        this.hour = hour;
    }

    public boolean sameSchedule(Schedule schedule) {
        return this.day == schedule.getDay() && this.hour == schedule.getHour();
    }

    public Day getDay() {
        return  this.day;
    }

    public int getHour() {
        return this.hour;
    }

}
