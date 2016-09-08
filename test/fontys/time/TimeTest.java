package fontys.time;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author Frank Peeters, Nico Kuijpers
 */

public class TimeTest {

    @Test
    public void testTimeConstructor() {

        // Date: Saturday 6th December 2014
        // Time: 14.45
        ITime time1 = new Time(2014, 12, 6, 14, 45);
        Assert.assertEquals("day in week", DayInWeek.SAT, time1.getDayInWeek());
        Assert.assertEquals("year", 2014, time1.getYear());
        Assert.assertEquals("month", 12, time1.getMonth());
        Assert.assertEquals("day", 6, time1.getDay());
        Assert.assertEquals("hours", 14, time1.getHours());
        Assert.assertEquals("minutes", 45, time1.getMinutes());
    }

    @Test (expected=IllegalArgumentException.class)
    public void testMonthTooSmall()
    {
        // Month < 1
        ITime time1 = new Time(2000, 0, 1, 1, 1);
    }

    @Test (expected=IllegalArgumentException.class)
    public void testMonthTooLarge()
    {
        // Month > 12
        ITime time1 = new Time(2000, 13, 1, 1, 1);
    }

    @Test (expected=IllegalArgumentException.class)
    public void testDayTooSmall()
    {
        // Day < 1
        ITime time1 = new Time(2000, 1, 0, 1, 1);
    }

    @Test (expected=IllegalArgumentException.class)
    public void testDayTooLarge()
    {
        // Day > 31
        ITime time1 = new Time(2000, 1, 32, 1, 1);
    }

    @Test (expected=IllegalArgumentException.class)
    public void testHourTooSmall()
    {
        // Hour < 0
        ITime time1 = new Time(2000, 1, 1, -1, 1);
    }

    @Test (expected=IllegalArgumentException.class)
    public void testHourTooLarge()
    {
        // Hour > 23
        ITime time1 = new Time(2000, 1, 1, 24, 1);
    }

    @Test (expected=IllegalArgumentException.class)
    public void testMinuteTooSmall()
    {
        // Minute < 0
        ITime time1 = new Time(2000, 1, 1, 1, -1);
    }

    @Test (expected=IllegalArgumentException.class)
    public void testMinuteTooLarge()
    {
        // Minute > 59
        ITime time1 = new Time(2000, 1, 1, 1, 60);
    }

    @Test
    public void testTimePlus() {

        /**
          *
          * @param minutes (a negative value is allowed)
          * @return  this time plus minutes
          */

        // Date: Tuesday 18th November 2014
        // Time: 14.45
        ITime time1 = new Time(2014, 11, 18, 14, 45);

        // Add 1 minute
        ITime time2 = time1.plus(1);
        Assert.assertEquals("day in week", DayInWeek.TUE, time2.getDayInWeek());
        Assert.assertEquals("year", 2014, time2.getYear());
        Assert.assertEquals("month", 11, time2.getMonth());
        Assert.assertEquals("day", 18, time2.getDay());
        Assert.assertEquals("hours", 14, time2.getHours());
        Assert.assertEquals("minutes", 46, time2.getMinutes());

        // Add 1 hour
        time2 = time1.plus(60);
        Assert.assertEquals("day in week", DayInWeek.TUE, time2.getDayInWeek());
        Assert.assertEquals("year", 2014, time2.getYear());
        Assert.assertEquals("month", 11, time2.getMonth());
        Assert.assertEquals("day", 18, time2.getDay());
        Assert.assertEquals("hours", 15, time2.getHours());
        Assert.assertEquals("minutes", 45, time2.getMinutes());

        // Add 1 day
        time2 = time1.plus(24 * 60);
        Assert.assertEquals("day in week", DayInWeek.WED, time2.getDayInWeek());
        Assert.assertEquals("year", 2014, time2.getYear());
        Assert.assertEquals("month", 11, time2.getMonth());
        Assert.assertEquals("day", 19, time2.getDay());
        Assert.assertEquals("hours", 14, time2.getHours());
        Assert.assertEquals("minutes", 45, time2.getMinutes());

        // Add 1 month (30 days)
        time2 = time1.plus(30 * 24 * 60);
        Assert.assertEquals("day in week", DayInWeek.THU, time2.getDayInWeek());
        Assert.assertEquals("year", 2014, time2.getYear());
        Assert.assertEquals("month", 12, time2.getMonth());
        Assert.assertEquals("day", 18, time2.getDay());
        Assert.assertEquals("hours", 14, time2.getHours());
        Assert.assertEquals("minutes", 45, time2.getMinutes());

        // Add 1 year (365 days)
        time2 = time1.plus(365 * 24 * 60);
        Assert.assertEquals("day in week", DayInWeek.WED, time2.getDayInWeek());
        Assert.assertEquals("year", 2015, time2.getYear());
        Assert.assertEquals("month", 11, time2.getMonth());
        Assert.assertEquals("day", 18, time2.getDay());
        Assert.assertEquals("hours", 14, time2.getHours());
        Assert.assertEquals("minutes", 45, time2.getMinutes());

        // Subtract 1 day
        time2 = time1.plus(-24 * 60);
        Assert.assertEquals("day in week", DayInWeek.MON, time2.getDayInWeek());
        Assert.assertEquals("year", 2014, time2.getYear());
        Assert.assertEquals("month", 11, time2.getMonth());
        Assert.assertEquals("day", 17, time2.getDay());
        Assert.assertEquals("hours", 14, time2.getHours());
        Assert.assertEquals("minutes", 45, time2.getMinutes());
    }

    @Test
    public void testTimeDifference()
    {
        /**
         * @param time
         * @return the difference between this time and [time] expressed in minutes
         */
        ITime time1 = new Time(2014, 12, 6, 10, 45);
        ITime time2 = new Time(2014, 12, 6, 11, 45);
        // Difference between time1 and time2 should be -60 min
        long difference = time1.difference(time2);
        Assert.assertEquals("difference", -60, difference);
        // Difference between time2 and time1 should be 60 min
        difference = time2.difference(time1);
        Assert.assertEquals("difference", 60, difference);
        // Difference between time1 and time1 should be 0 min
        difference = time1.difference(time1);
        Assert.assertEquals("difference", 0, difference);
    }

    @Test
    public void testTimeCompareTo()
    {
        /**
         * @param time
         * @return -1 if this time is before [time]
         *          0 it this time is equal to [time]
         *          1 if this time is after [time]
         */
        ITime time1 = new Time(2014, 12, 6, 10, 45);
        ITime time2 = new Time(2014, 12, 6, 11, 45);
        ITime time3 = new Time(2014, 12, 6, 11, 45);
        // time1 is before time2
        Assert.assertEquals("compare to", -1, time1.compareTo(time2));
        // time2 is after time1
        Assert.assertEquals("compare to", 1, time2.compareTo(time1));
        // time3 is equal to time2
        Assert.assertEquals("compare to", 0, time3.compareTo(time2));
    }
}
