import exceptions.FullCourseException;
import exceptions.IncompatibleLevelsException;
import exceptions.IncompatibleModalityException;
import exceptions.IncompatibleScheduleException;
import schedule.Day;
import schedule.Schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Course {

    private Teacher teacher;
    private Level level;
    private Schedule schedule;
    private List<Student> enrolledStudents = new ArrayList<>();
    private List<Student> rejectedStudents = new ArrayList<>();
    private Modality modality;

    public Course(Schedule schedule, Level level, Modality modality) {
        this.schedule = schedule;
        this.level = level;
        this.modality = modality;
    }

    public void assignTeacher(Teacher teacher) {
        if (canNotAdaptToSchedule(teacher)) throw new IncompatibleScheduleException();
        this.teacher = teacher;
    }

    public void addStudents(List<Student> newStudents) {
        try {
            validateStudentsConditions(newStudents);
            enrolledStudents.addAll(newStudents);
        } catch (RuntimeException exception) {
            rejectedStudents.addAll(newStudents);
            throw exception;
        }
    }

    private void validateStudentsConditions(List<Student> newStudents) {
        if (newStudents.stream().anyMatch(student -> student.hasDifferentLevel(level))) throw new IncompatibleLevelsException();
        if (newStudents.stream().anyMatch(student -> student.hasDifferentModality(modality))) throw new IncompatibleModalityException();
        if (modality.doesNotAllow(enrolledStudents.size() + newStudents.size())) throw new FullCourseException();
        if (newStudents.stream().anyMatch(this::canNotAdaptToSchedule)) throw new IncompatibleScheduleException();
    }

    private boolean canNotAdaptToSchedule(Availability person) {
        return person.canNotAdaptToSchedule(schedule);
    }

    public Teacher getTeacher() {
        return this.teacher;
    }

    public List<Student> getEnrolledStudents() {
        return this.enrolledStudents;
    }

    public List<Student> getRejectedStudents() {
        return this.rejectedStudents;
    }
}
