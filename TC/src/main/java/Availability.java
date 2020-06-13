import schedule.Schedule;

import java.util.ArrayList;
import java.util.List;

public abstract class Availability {

    protected List<Schedule> schedules = new ArrayList<>();

    public boolean canNotAdaptToSchedule(Schedule schedule) {
        return schedules.isEmpty() || schedules.stream().noneMatch(s -> s.sameSchedule(schedule));
    }

}
