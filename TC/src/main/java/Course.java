import schedule.Schedule;

import java.util.ArrayList;
import java.util.List;

public class Course {

    private Schedule schedule;
    private Teacher teacher;
    private List<Student> enrolledStudents = new ArrayList<>();
    private Level level;
    private Modality modality;

    public Course(Schedule schedule, Teacher teacher) {
        this.schedule = schedule;
        this.teacher = teacher;
    }

    public Schedule getSchedule() {
        return this.schedule;
    }

    public Teacher getTeacher() {
        return this.teacher;
    }

    public boolean hasModality(Modality modality) {
        return this.modality == modality;
    }

    public void enrollStudents(Student student) {
        enrolledStudents.add(student);
    }

    public void enrollStudents(List<Student> students) {
        enrolledStudents.addAll(students);
    }

    public List<Student> getEnrolledStudents() {
        return this.enrolledStudents;
    }

    public void assignLevel(Level level) {
        this.level = level;
    }

    public void assignModality(Modality modality) {
        this.modality = modality;
    }

    public Level getLevel() {
        return this.level;
    }

    public Modality getModality() {
        return this.modality;
    }

}
