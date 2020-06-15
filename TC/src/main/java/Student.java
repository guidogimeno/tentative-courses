import schedule.Schedule;
import java.util.List;


public class Student {

    private Level level;
    private Modality modality = Modality.INDIVIDUAL;
    private List<Schedule> schedules;

    public Student(List<Schedule> schedules) {
        this.schedules = schedules;
        this.level = Level.BEGINNER;
    }

    public boolean isAvaileableOn(Schedule schedule) {
        return schedules.stream().anyMatch(s -> s.sameSchedule(schedule));
    }

    public boolean hasLevel(Level level) {
        return this.level == level;
    }

    public boolean hasModality(Modality modality) {
        return this.modality == modality;
    }

    public Level getLevel() {
        return this.level;
    }

    public void changeLevel(Level level) {
        this.level = level;
    }

    public Modality getModality() {
        return this.modality;
    }

    public void changeModality(Modality modality) {
        this.modality = modality;
    }

}
