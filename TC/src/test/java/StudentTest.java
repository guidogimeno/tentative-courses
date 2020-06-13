
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import schedule.Day;
import schedule.Schedule;

import static org.hamcrest.CoreMatchers.is;

public class StudentTest {

    private Student beginnerStudent;
    private Student preIntermediateStudent;
    private Student intermediateStudent;
    private Student upperIntermediateStudent;
    private Student advancedStudent;

    @Before
    public void studentsCreation() {
        beginnerStudent = new Student();
        preIntermediateStudent = new Student(Level.PRE_INTERMEDIATE);
        intermediateStudent = new Student(Level.INTERMEDIATE);
        upperIntermediateStudent = new Student(Level.UPPER_INTERMEDIATE);
        advancedStudent = new Student(Level.ADVANCED);
    }

    @Test
    public void studentCanMoveToAnotherLevel() {
        /* Starting Level */
        Assert.assertEquals(beginnerStudent.getLevel(), Level.BEGINNER);
        Assert.assertEquals(preIntermediateStudent.getLevel(), Level.PRE_INTERMEDIATE);
        Assert.assertEquals(intermediateStudent.getLevel(), Level.INTERMEDIATE);
        Assert.assertEquals(upperIntermediateStudent.getLevel(), Level.UPPER_INTERMEDIATE);
        Assert.assertEquals(advancedStudent.getLevel(), Level.ADVANCED);

        /* Changes */
        beginnerStudent.changeLevel(Level.PRE_INTERMEDIATE);
        preIntermediateStudent.changeLevel(Level.BEGINNER);
        intermediateStudent.changeLevel(Level.ADVANCED);
        upperIntermediateStudent.changeLevel(Level.BEGINNER);
        advancedStudent.changeLevel(Level.UPPER_INTERMEDIATE);

        /* Final Level */
        Assert.assertEquals(beginnerStudent.getLevel(), Level.PRE_INTERMEDIATE);
        Assert.assertEquals(preIntermediateStudent.getLevel(), Level.BEGINNER);
        Assert.assertEquals(intermediateStudent.getLevel(), Level.ADVANCED);
        Assert.assertEquals(upperIntermediateStudent.getLevel(), Level.BEGINNER);
        Assert.assertEquals(advancedStudent.getLevel(), Level.UPPER_INTERMEDIATE);
    }

    @Test
    public void defaultStudentHasIndividualModality() {
        Assert.assertThat(beginnerStudent.getModality(), is(Modality.INDIVIDUAL));
    }

    @Test
    public void studentCanChangeModality() {
        beginnerStudent.changeModality(Modality.GROUP);
        Assert.assertThat(beginnerStudent.getModality(), is(Modality.GROUP));
    }

    @Test
    public void studentCanNotAdaptToSchedule() {
        beginnerStudent.addSchedule(new Schedule(Day.WEDNESDAY, 14));
        Assert.assertTrue(beginnerStudent.canNotAdaptToSchedule(new Schedule(Day.MONDAY, 17)));
    }

    @Test
    public void studentCanAdaptToSchedule() {
        beginnerStudent.addSchedule(new Schedule(Day.WEDNESDAY, 14));
        Assert.assertFalse(beginnerStudent.canNotAdaptToSchedule(new Schedule(Day.WEDNESDAY, 14)));
    }

}
