import exceptions.FullCourseException;
import exceptions.IncompatibleLevelsException;
import exceptions.IncompatibleModalityException;
import exceptions.IncompatibleScheduleException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import schedule.Day;
import schedule.Schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseTest {

    private List<Schedule> schedules = new ArrayList<>();
    private Teacher teacher;
    private List<Student> students = new ArrayList<>();
    private Schedule schedule;

    @Before
    public void objectsCreation() {
        schedules.add(new Schedule(Day.TUESDAY, 13));
        schedules.add(new Schedule(Day.FRIDAY, 19));

        teacher = new Teacher(schedules);

        // 6 Beginners
        students.add(new Student());
        students.add(new Student());
        students.add(new Student());
        students.add(new Student());
        students.add(new Student());
        students.add(new Student());

        schedule = new Schedule(Day.MONDAY, 16);
    }

    @Test
    public void courseRespectsTeachersSchedule() {
        Schedule courseSchedule = new Schedule(Day.FRIDAY, 19);

        Course course = new Course(courseSchedule, Level.BEGINNER, Modality.INDIVIDUAL);

        course.assignTeacher(teacher);

        Assert.assertEquals(teacher, course.getTeacher());
    }

    @Test(expected = IncompatibleScheduleException.class)
    public void courseDoesNotRespectTeachersSchedule() {
        Schedule courseSchedule = new Schedule(Day.THURSDAY, 12);

        Course course = new Course(courseSchedule, Level.BEGINNER, Modality.INDIVIDUAL);

        course.assignTeacher(teacher);
    }

    @Test
    public void courseRespectsStudentsSchedule() {
        Course course = new Course(schedule, Level.BEGINNER, Modality.INDIVIDUAL);

        Student englishStudent = new Student();
        englishStudent.addSchedule(schedule);

        course.addStudents(Arrays.asList(englishStudent));

        Assert.assertTrue(course.getEnrolledStudents().contains(englishStudent));
    }

    @Test(expected = IncompatibleScheduleException.class)
    public void courseDoesNotRespectsStudentsSchedule() {
        Schedule courseSchedule = new Schedule(Day.MONDAY, 13);
        Schedule studentSchedule = new Schedule(Day.MONDAY, 12);

        Course course = new Course(courseSchedule, Level.BEGINNER, Modality.INDIVIDUAL);

        Student englishStudent = new Student();
        englishStudent.addSchedule(studentSchedule);

        course.addStudents(Arrays.asList(englishStudent));
    }

    @Test
    public void courseAllowsStudentsWithEqualLevel() {
        Course course = new Course(schedule, Level.BEGINNER, Modality.GROUP);

        students.forEach(student -> {
            student.addSchedule(schedule);
            student.changeModality(Modality.GROUP);
        });

        course.addStudents(students);

        Assert.assertEquals(students, course.getEnrolledStudents());
    }

    @Test(expected = IncompatibleLevelsException.class)
    public void courseDoesNotAllowStudentsWithDifferentLevels() {
        Course course = new Course(schedule, Level.BEGINNER, Modality.GROUP);

        students.forEach(student -> {
            student.addSchedule(schedule);
            student.changeModality(Modality.GROUP);
        });
        students.get(0).changeLevel(Level.PRE_INTERMEDIATE);

        course.addStudents(students);
    }

    @Test(expected = FullCourseException.class)
    public void groupCourseDoesNotAllowsMoreThan6Students() {
        Course course = new Course(schedule, Level.BEGINNER, Modality.GROUP);

        Student newStudent = new Student();

        students.add(newStudent);
        students.forEach(student -> {
            student.addSchedule(schedule);
            student.changeModality(Modality.GROUP);
        });

        course.addStudents(students);
    }

    @Test(expected = FullCourseException.class)
    public void individualCourseDoesNotAllowsMoreThan1Student() {
        Course course = new Course(schedule, Level.BEGINNER, Modality.INDIVIDUAL);

        students.forEach(student -> student.addSchedule(schedule));

        course.addStudents(students);
    }

    @Test(expected = IncompatibleModalityException.class)
    public void groupCourseDoesNotAllowIndividualStudent() {
        Course course = new Course(schedule, Level.BEGINNER, Modality.GROUP);

        Student student = new Student();

        course.addStudents(Arrays.asList(student));
    }

    @Test(expected = IncompatibleModalityException.class)
    public void individualGroupDoesNotAllowGroupStudent() {
        Course course = new Course(schedule, Level.BEGINNER, Modality.INDIVIDUAL);

        Student student = new Student();
        student.changeModality(Modality.GROUP);

        course.addStudents(Arrays.asList(student));
    }

    /* BONUS */
    @Test(expected = FullCourseException.class)
    public void rejectedStudentsGetStored() {
        Course course = new Course(schedule, Level.BEGINNER, Modality.INDIVIDUAL);

        try {
            course.addStudents(students);
        } catch (RuntimeException exception) {
            Assert.assertEquals(students, course.getRejectedStudents());
            throw exception;
        }
    }

}
