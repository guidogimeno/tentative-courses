import exceptions.IncompatibleScheduleException;
import org.junit.Test;
import schedule.Day;
import schedule.Schedule;

public class ScheduleTest {

    @Test(expected = IncompatibleScheduleException.class)
    public void invalidTimeForScheduleThrowsException() {
        new Schedule(Day.THURSDAY, 8);
    }


    @Test
    public void validTimeForScheduleThrowsException() {
        new Schedule(Day.MONDAY, 9);
    }

}
