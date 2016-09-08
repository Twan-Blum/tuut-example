package fontys.time;

import com.fontys.tuut.annotation.TUUTTest;
import com.fontys.tuut.annotation.TUUTTestConstructor;
import com.fontys.tuut.annotation.TUUTTestMethod;

@TUUTTest("fontys.time.TimeSpan")
public class TimeSpanTestError
{
    private ITime bt, et;

    @TUUTTestConstructor(
        weight      = 10,
        description = "end time should be later then begin time"
    )
    public TimeSpanTestError(ITime bt, ITime et) {
        if (bt.compareTo(et) <= 0) {
            throw new IllegalArgumentException("begin time "
                    + bt + " must be earlier than end time " + et);
        }
        this.bt = bt;
        this.et = et;
    }

    @TUUTTestMethod(
        name        = "setEndTime",
        weight      = 10,
        description = "end time sets begin time instead of end time"
    )
    public void setEndTime(ITime endTime) {
        if (endTime.compareTo(bt) <= 0) {
            throw new IllegalArgumentException("end time "
                    + et + " must be later then begin time " + bt);
        }

        bt = endTime;
    }

    @TUUTTestMethod(
        name        = "move",
        weight      = 10,
        description = "move moves begin time twice"
    )
    public void move(int minutes) {
        bt = bt.plus(minutes);
        et = bt.plus(minutes);
    }
    
    @TUUTTestMethod(
        name        = "changeLengthWith",
        weight      = 10,
        description = ""
    )
    public void changeLengthWith(int minutes) {
        if (minutes <= 0) {
             throw new IllegalArgumentException("length of period must be positive");
         }
    }

}
