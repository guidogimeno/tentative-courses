import schedule.Schedule;

import java.util.ArrayList;
import java.util.List;

public class Teacher {

    private List<Schedule> schedules;

    public Teacher(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public List<Schedule> getSchedules() {
        return this.schedules;
    }

}
