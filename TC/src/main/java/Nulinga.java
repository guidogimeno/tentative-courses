import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Nulinga {

    private List<Teacher> teachers = new ArrayList<>();
    private List<Student> students = new ArrayList<>();

    public List<Course> getPossibleCourses() {
        // Courses with teachers and a schedule
        List<Course> coursesWithTeacher = createCoursesWithTeachers();

        // Add students by schedule
        coursesWithTeacher.forEach(course -> students.forEach(student -> {
            if (student.isAvaileableOn(course.getSchedule())) course.enrollStudents(student);
        }));

        // Divide by Level
        List<Course> coursesDividedByLevel = getCoursesDividedByLevel(coursesWithTeacher);

        // Divide by Modality
        return getCoursesDividedByModality(coursesDividedByLevel);
    }

    private List<Course> createCoursesWithTeachers() {
        return teachers.stream()
                .map(teacher -> teacher.getSchedules().stream()
                        .map(schedule -> new Course(schedule, teacher))
                        .collect(Collectors.toList()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<Course> getCoursesDividedByLevel(List<Course> courses) {
        return courses.stream()
                .map(course -> course.getEnrolledStudents().stream()
                        .map(Student::getLevel)
                        .distinct()
                        .map(level -> {
                            List<Student> students = course.getEnrolledStudents().stream()
                                    .filter(student -> student.hasLevel(level)).collect(Collectors.toList());
                            Course courseWithLevel = new Course(course.getSchedule(), course.getTeacher());
                            courseWithLevel.enrollStudents(students);
                            courseWithLevel.assignLevel(level);
                            return courseWithLevel;
                        })
                        .collect(Collectors.toList()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<Course> getCoursesDividedByModality(List<Course> courses) {
        return courses.stream()
                .map(course -> course.getEnrolledStudents().stream()
                        .map(Student::getModality)
                        .distinct()
                        .map(modality -> {
                            List<Student> students = course.getEnrolledStudents().stream()
                                    .filter(student -> student.hasModality(modality))
                                    .limit(modality.maximumAllowed())
                                    .collect(Collectors.toList());
                            Course courseWithModality = new Course(course.getSchedule(), course.getTeacher());
                            courseWithModality.assignLevel(course.getLevel());
                            courseWithModality.assignModality(modality);
                            courseWithModality.enrollStudents(students);
                            return courseWithModality;
                        })
                        .collect(Collectors.toList()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public void loadTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public void loadStudents(List<Student> students) {
        this.students = students;
    }

    public List<Student> getRejectedStudents() {
        List<Student> enrolledStudents = getPossibleCourses().stream()
                .map(Course::getEnrolledStudents)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        List<Student> rejectedStudents = new ArrayList<>(students);
        rejectedStudents.removeAll(enrolledStudents);

        return rejectedStudents;
    }
}
