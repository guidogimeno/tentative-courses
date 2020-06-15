import org.junit.Before;
import org.junit.Test;
import schedule.Day;
import schedule.Schedule;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class CourseTests {

    private Schedule monday = scheduleCreator(Day.MONDAY, 9);
    private Schedule tuesday = scheduleCreator(Day.TUESDAY, 9);
    private Schedule wednesday = scheduleCreator(Day.WEDNESDAY, 9);
    private Schedule thursday = scheduleCreator(Day.THURSDAY, 9);
    private Schedule friday = scheduleCreator(Day.FRIDAY, 9);

    private Nulinga nulinga;

    @Before
    public void setup() {
        nulinga = new Nulinga();
    }

    private Schedule scheduleCreator(Day day, int hour) {
        return new Schedule(day, hour);
    }

    private Teacher teacherCreator(List<Schedule> schedules) {
        return new Teacher(schedules);
    }

    private Student studentCreator(List<Schedule> schedules, Modality modality, Level level) {
        Student student = new Student(schedules);
        student.changeModality(modality);
        student.changeLevel(level);
        return student;
    }

    private List<Course> courseOf1TeacherAnd1Student() {
        Teacher teacher = teacherCreator(Arrays.asList(monday, tuesday));
        Student student = new Student(Arrays.asList(tuesday, wednesday));

        nulinga.loadStudents(Arrays.asList(student));
        nulinga.loadTeachers(Arrays.asList(teacher));

        return nulinga.getPossibleCourses();
    }

    @Test
    public void intersectionBetweenTeacherAndStudentSchedulesResultsInOneCourse() {
        assertEquals(1, courseOf1TeacherAnd1Student().size());
    }

    @Test
    public void courseContainsATeacher() {
        Course course = courseOf1TeacherAnd1Student().get(0);
        assertNotNull(course.getTeacher());
    }

    @Test
    public void courseContainsAStudent() {
        Course course = courseOf1TeacherAnd1Student().get(0);
        assertNotNull(course.getTeacher());
    }

    @Test
    public void courseHasALevel() {
        Course course = courseOf1TeacherAnd1Student().get(0);
        assertNotNull(course.getLevel());
    }

    @Test
    public void courseHasIndividualModality() {
        Course course = courseOf1TeacherAnd1Student().get(0);
        assertEquals(Modality.INDIVIDUAL, course.getModality());
    }

    @Test
    public void courseHasBeginnerLevel() {
        Course course = courseOf1TeacherAnd1Student().get(0);
        assertEquals(Level.BEGINNER, course.getLevel());
    }

    @Test
    public void intersectionBetweenStudentAndTeacherSchedulesIsEmpty() {
        Teacher teacher = teacherCreator(Arrays.asList(monday, tuesday));
        Student student = new Student(Arrays.asList(thursday, wednesday));

        nulinga.loadStudents(Arrays.asList(student));
        nulinga.loadTeachers(Arrays.asList(teacher));

        assertEquals(0, nulinga.getPossibleCourses().size());
    }

    @Test
    public void coursesGetDividedByStudentsSchedule() {
        Teacher teacher = teacherCreator(Arrays.asList(monday, tuesday, thursday));
        Student student1 = studentCreator(Arrays.asList(monday), Modality.GROUP, Level.BEGINNER);
        Student student2 = studentCreator(Arrays.asList(tuesday), Modality.GROUP, Level.BEGINNER);
        Student student3 = studentCreator(Arrays.asList(thursday), Modality.GROUP, Level.BEGINNER);

        nulinga.loadStudents(Arrays.asList(student1, student2, student3));
        nulinga.loadTeachers(Arrays.asList(teacher));

        assertEquals(3, nulinga.getPossibleCourses().size());
    }

    @Test
    public void sameStudentCanAttendToDifferentCourses() {
        Teacher teacher1 = teacherCreator(Arrays.asList(monday, tuesday));
        Teacher teacher2 = teacherCreator(Arrays.asList(thursday));
        Student student = studentCreator(Arrays.asList(monday, tuesday, thursday), Modality.GROUP, Level.BEGINNER);

        nulinga.loadStudents(Arrays.asList(student));
        nulinga.loadTeachers(Arrays.asList(teacher1, teacher2));

        assertEquals(3, nulinga.getPossibleCourses().size());
    }

    @Test
    public void coursesGetDividedByLevel() {
        Teacher teacher = teacherCreator(Arrays.asList(monday));
        Student student1 = studentCreator(Arrays.asList(monday), Modality.GROUP, Level.BEGINNER);
        Student student2 = studentCreator(Arrays.asList(monday), Modality.GROUP, Level.UPPER_INTERMEDIATE);
        Student student3 = studentCreator(Arrays.asList(monday), Modality.GROUP, Level.ADVANCED);

        nulinga.loadStudents(Arrays.asList(student1, student2, student3));
        nulinga.loadTeachers(Arrays.asList(teacher));

        assertEquals(3, nulinga.getPossibleCourses().size());
    }

    @Test
    public void coursesGetDividedByModality() {
        Teacher teacher = teacherCreator(Arrays.asList(monday));
        Student student1 = studentCreator(Arrays.asList(monday), Modality.INDIVIDUAL, Level.BEGINNER);
        Student student2 = studentCreator(Arrays.asList(monday), Modality.GROUP, Level.BEGINNER);

        nulinga.loadStudents(Arrays.asList(student1, student2));
        nulinga.loadTeachers(Arrays.asList(teacher));

        assertEquals(2, nulinga.getPossibleCourses().size());
    }

    @Test
    public void coursesGetDividedByTeachers() {
        Teacher teacher1 = teacherCreator(Arrays.asList(monday, tuesday));
        Teacher teacher2 = teacherCreator(Arrays.asList(monday));
        Student student = studentCreator(Arrays.asList(monday), Modality.INDIVIDUAL, Level.BEGINNER);

        nulinga.loadStudents(Arrays.asList(student));
        nulinga.loadTeachers(Arrays.asList(teacher1, teacher2));

        assertEquals(2, nulinga.getPossibleCourses().size());
    }

    @Test
    public void onlyOneCourseForManyIndividuals() {
        Teacher teacher = teacherCreator(Arrays.asList(monday));
        Student student1 = studentCreator(Arrays.asList(monday), Modality.INDIVIDUAL, Level.BEGINNER);
        Student student2 = studentCreator(Arrays.asList(monday), Modality.INDIVIDUAL, Level.BEGINNER);
        Student student3 = studentCreator(Arrays.asList(monday), Modality.INDIVIDUAL, Level.BEGINNER);

        nulinga.loadStudents(Arrays.asList(student1, student2, student3));
        nulinga.loadTeachers(Arrays.asList(teacher));

        assertEquals(1, nulinga.getPossibleCourses().size());
    }

    @Test
    public void groupCourseHasUpTo6Students() {
        Teacher teacher = teacherCreator(Arrays.asList(monday));
        Student student1 = studentCreator(Arrays.asList(monday), Modality.GROUP, Level.BEGINNER);
        Student student2 = studentCreator(Arrays.asList(monday), Modality.GROUP, Level.BEGINNER);
        Student student3 = studentCreator(Arrays.asList(monday), Modality.GROUP, Level.BEGINNER);
        Student student4 = studentCreator(Arrays.asList(monday), Modality.GROUP, Level.BEGINNER);
        Student student5 = studentCreator(Arrays.asList(monday), Modality.GROUP, Level.BEGINNER);
        Student student6 = studentCreator(Arrays.asList(monday), Modality.GROUP, Level.BEGINNER);
        Student student7 = studentCreator(Arrays.asList(monday), Modality.GROUP, Level.BEGINNER);

        nulinga.loadStudents(Arrays.asList(student1, student2, student3, student4, student5, student6, student7));
        nulinga.loadTeachers(Arrays.asList(teacher));

        assertEquals(1, nulinga.getPossibleCourses().size());
        assertEquals(6, nulinga.getPossibleCourses().get(0).getEnrolledStudents().size());
    }

    public List<Course> combiningEverything() {
        Teacher teacher1 = teacherCreator(Arrays.asList(monday, tuesday));
        Teacher teacher2 = teacherCreator(Arrays.asList(wednesday));
        Teacher teacher3 = teacherCreator(Arrays.asList(thursday));
        Teacher teacher4 = teacherCreator(Arrays.asList(tuesday, thursday));

        Student student1 = studentCreator(Arrays.asList(monday), Modality.GROUP, Level.BEGINNER);
        Student student2 = studentCreator(Arrays.asList(monday), Modality.GROUP, Level.ADVANCED);
        Student student3 = studentCreator(Arrays.asList(monday), Modality.GROUP, Level.UPPER_INTERMEDIATE);
        Student student4 = studentCreator(Arrays.asList(monday), Modality.GROUP, Level.INTERMEDIATE);
        Student student5 = studentCreator(Arrays.asList(monday), Modality.GROUP, Level.PRE_INTERMEDIATE);
        Student student6 = studentCreator(Arrays.asList(monday), Modality.GROUP, Level.ADVANCED);

        Student student7 = studentCreator(Arrays.asList(tuesday), Modality.INDIVIDUAL, Level.BEGINNER);
        Student student8 = studentCreator(Arrays.asList(tuesday), Modality.INDIVIDUAL, Level.BEGINNER);
        Student student9 = studentCreator(Arrays.asList(tuesday), Modality.INDIVIDUAL, Level.BEGINNER);

        Student student10 = studentCreator(Arrays.asList(monday, tuesday), Modality.GROUP, Level.BEGINNER);
        Student student11 = studentCreator(Arrays.asList(tuesday, thursday), Modality.GROUP, Level.BEGINNER);
        Student student12 = studentCreator(Arrays.asList(friday), Modality.GROUP, Level.BEGINNER);
        Student student13 = studentCreator(Arrays.asList(monday, wednesday), Modality.GROUP, Level.BEGINNER);

        Student student14 = studentCreator(Arrays.asList(wednesday), Modality.GROUP, Level.BEGINNER);
        Student student15 = studentCreator(Arrays.asList(wednesday), Modality.GROUP, Level.BEGINNER);
        Student student16 = studentCreator(Arrays.asList(wednesday), Modality.GROUP, Level.BEGINNER);
        Student student17 = studentCreator(Arrays.asList(wednesday), Modality.GROUP, Level.BEGINNER);
        Student student18 = studentCreator(Arrays.asList(wednesday), Modality.GROUP, Level.BEGINNER);
        Student student19 = studentCreator(Arrays.asList(wednesday), Modality.GROUP, Level.BEGINNER);
        Student student20 = studentCreator(Arrays.asList(wednesday), Modality.GROUP, Level.BEGINNER);

        nulinga.loadStudents(Arrays.asList(student1, student2, student3, student4, student5, student6, student7, student8,
                student9, student10, student11, student12, student13, student14, student15, student16,
                student17, student18, student19, student20));
        nulinga.loadTeachers(Arrays.asList(teacher1, teacher2, teacher3, teacher4));

        return nulinga.getPossibleCourses();
    }

    @Test
    public void amountOfCourses() {
        assertEquals(12, combiningEverything().size());
    }

    @Test
    public void fourTeachersInTotal() {
        assertEquals(4, combiningEverything().stream().map(Course::getTeacher).distinct().count());
    }

    @Test
    public void fourDatesInTotal() {
        assertEquals(4, combiningEverything().stream().map(Course::getSchedule).distinct().count());
    }

    @Test
    public void individualsDontExceedTheMaximum() {
        assertTrue(combiningEverything().stream()
                .filter(course -> course.hasModality(Modality.INDIVIDUAL))
                .allMatch(course -> course.getEnrolledStudents().size() == 1));
    }

    @Test
    public void groupsDontExceedTheMaximun() {
        assertTrue(combiningEverything().stream()
                .filter(course -> course.hasModality(Modality.GROUP))
                .allMatch(course -> course.getEnrolledStudents().size() <= 6));
    }

    @Test
    public void enrolledStudents() {
        assertEquals(15, combiningEverything().stream().map(Course::getEnrolledStudents).flatMap(List::stream).distinct().count());
    }

    @Test
    public void noCoursesOnFriday() {
        assertEquals(0, combiningEverything().stream().filter(course -> course.getSchedule().equals(friday)).count());
    }

    @Test
    public void getRejecteds() {
        Teacher teacher = teacherCreator(Arrays.asList(monday, friday));
        Student student1 = studentCreator(Arrays.asList(monday), Modality.GROUP, Level.BEGINNER);
        Student student2 = studentCreator(Arrays.asList(monday), Modality.GROUP, Level.BEGINNER);
        Student student3 = studentCreator(Arrays.asList(monday), Modality.GROUP, Level.BEGINNER);
        Student student4 = studentCreator(Arrays.asList(tuesday), Modality.GROUP, Level.BEGINNER);
        Student student5 = studentCreator(Arrays.asList(wednesday), Modality.GROUP, Level.BEGINNER);
        Student student6 = studentCreator(Arrays.asList(friday), Modality.INDIVIDUAL, Level.BEGINNER);
        Student student7 = studentCreator(Arrays.asList(friday), Modality.INDIVIDUAL, Level.BEGINNER);

        nulinga.loadStudents(Arrays.asList(student1, student2, student3, student4, student5, student6, student7));
        nulinga.loadTeachers(Arrays.asList(teacher));

        assertEquals(3, nulinga.getRejectedStudents().size());
        assertTrue(nulinga.getRejectedStudents().containsAll(Arrays.asList(student4, student5, student7)));
    }
}
