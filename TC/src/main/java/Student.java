import schedule.Schedule;

import java.util.List;


public class Student extends Availability {

    private Level level;
    private Modality modality = Modality.INDIVIDUAL;

    public Student() {
        this.level = Level.BEGINNER;
    }

    public Student(Level level) {
        this.level = level;
    }

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    public boolean hasDifferentLevel(Level level) {
        return this.level != level;
    }

    public boolean hasDifferentModality(Modality modality) {
        return this.modality != modality;
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

    public List<Schedule> getSchedules() {
        return this.schedules;
    }

}
