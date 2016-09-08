/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import com.fontys.tuut.annotation.TUUTTest;
import com.fontys.tuut.annotation.TUUTTestConstructor;
import com.fontys.tuut.annotation.TUUTTestMethod;
import com.fontys.tuut.runner.TuutException;
import java.lang.reflect.Field;
import java.util.GregorianCalendar;

/**
 *
 * @author Twan Blum
 */
@TUUTTest("fontys.time.Time")
public class TimeTestError
{
    private GregorianCalendar gc;
    
    @TUUTTestConstructor(
        weight = 10,
        description = "use of monts instead of minutes"
            
    )
    public TimeTestError(int y, int m, int d, int h, int min) {
        if (m < 1 || m > 12) {
            throw new IllegalArgumentException("month must be within 1..12");
        }
        if (d < 1 || d > 31) {
            throw new IllegalArgumentException("day must be within 1..31");
        }
        if (h < 0 || h > 23) {
            throw new IllegalArgumentException("hours must be within 0..23");
        }

        if (m < 0 || m > 59) {
            throw new IllegalArgumentException("minutes must be within 0..59");
        }
    }
    
    @TUUTTestMethod(
        name = "compareTo",
        weight = 10,
        description = "comares time to gc"
    )
    public int compareToTimeToGc(ITime t) throws TuutException {
        Time time = (Time) t;
        
        try {
            // have to use reflection to access private fields
            Field privateGcField;
            privateGcField = Time.class.getDeclaredField("gc");
            privateGcField.setAccessible(true);
            GregorianCalendar gcTime = (GregorianCalendar) privateGcField.get(time);
            
            // FOUT
            // return time.gc.compareTo(gc);
            return gcTime.compareTo(gc);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            throw new TuutException("could not modify gc field in time", ex);
        }
    }
    
    @TUUTTestMethod(
        name = "difference",
        weight = 10,
        description = "difference in hours"
    )
    public int differenceInHours(ITime time) throws TuutException {
        Time t = (Time) time;
  
        try {
            // have to use reflection to access private fields
            Field privateGcField;
            privateGcField = Time.class.getDeclaredField("gc");
            privateGcField.setAccessible(true);
            GregorianCalendar gcTime = (GregorianCalendar) privateGcField.get(time);
            
            // FOUT
            // return (int) ((this.gc.getTimeInMillis() - t.gc.getTimeInMillis()) / 600000);
            return (int) ((this.gc.getTimeInMillis() - gcTime.getTimeInMillis()) / 600000);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            throw new TuutException("could not modify gc field in time", ex);
        }
    }
    
    
}
