/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Frank Peeters
 */

public class TimeSpan2Test {

    private ITimeSpan timeSpan1;

    @Before
    public void setUp() {
        timeSpan1 = new TimeSpan2(new Time(2012, 03, 06, 8, 0), new Time(2012, 03, 06, 9, 30));
    }

    @Test
    public void testGetBeginTime() {

        Assert.assertTrue("begintime", (new Time(2012, 3, 6, 8, 0)).compareTo(timeSpan1.getBeginTime()) == 0);
    }

    @Test
    public void testGetEndTime() {

        Assert.assertTrue("begintime", (new Time(2012, 03, 06, 9, 30)).compareTo(timeSpan1.getEndTime()) == 0);
    }

    @Test
    public void testLength() {

        Assert.assertEquals("length", 90, timeSpan1.length());
    }

    @Test
    public void testSetBeginTime() {
        /**
         * beginTime will be the new begin time of this time span
         * @param beginTime must be earlier than the current end time
         * of this time span
         */
        try {
            timeSpan1.setBeginTime(new Time(2012, 03, 06, 9, 40));
            fail("begintime is not earlier");
        } catch (IllegalArgumentException exc) {
        }
        try {

            timeSpan1.setBeginTime(timeSpan1.getEndTime());
            fail("begintime is not earlier");
        } catch (IllegalArgumentException exc) {
        }

        timeSpan1.setBeginTime(timeSpan1.getBeginTime().plus(60));
        Assert.assertEquals("begintime plus 60", 30, timeSpan1.length());
    }

    @Test
    public void testSetEndTime() {
        /**
         * endTime will be the new end time of this time span
         * @param beginTime must be later than the current begin time
         * of this time span
         */
        try {
            timeSpan1.setEndTime(new Time(2012, 03, 05, 8, 0));
            fail("end time is not later");
        } catch (IllegalArgumentException exc) {
        }
        try {
            timeSpan1.setEndTime(timeSpan1.getBeginTime());
            fail("end time is not later");
        } catch (IllegalArgumentException exc) {
        }

        timeSpan1.setEndTime(timeSpan1.getEndTime().plus(-30));
        Assert.assertEquals("endtime minus 30", 60, timeSpan1.length());

    }

    @Test
    public void testMove() {
        /**
         * the begin and end time of this time span will be moved up both with [minutes]
         * minutes
         * @param minutes (a negative value is allowed)
         */
        long length = timeSpan1.length();
        timeSpan1.move(20);
        Assert.assertEquals("length has been changed", length, timeSpan1.length());
        Assert.assertTrue("begin time is not correct", timeSpan1.getBeginTime().compareTo(new Time(2012, 03, 06, 8, 20)) == 0);
        timeSpan1.move(-20);
        Assert.assertEquals("length has been changed", length, timeSpan1.length());
        Assert.assertTrue("begin time is not correct", timeSpan1.getBeginTime().compareTo(new Time(2012, 03, 06, 8, 0)) == 0);
        timeSpan1.move(90);
        Assert.assertEquals("length has been changed", length, timeSpan1.length());
        Assert.assertTrue("begin time is not correct", timeSpan1.getBeginTime().compareTo(new Time(2012, 03, 06, 9, 30)) == 0);

    }

    @Test
    public void testChangeLengthWith() {

        /**
         * the end time of this time span will be moved up with [minutes] minutes
         * @param minutes  minutes > -(length of this period)
         */
        try {
            timeSpan1.changeLengthWith(-timeSpan1.length());
            fail("final length must be positive");
        } catch (IllegalArgumentException exc) {
        }
        long length = timeSpan1.length();
        timeSpan1.changeLengthWith(20);
        Assert.assertEquals("length has been changed", length + 20, timeSpan1.length());
        Assert.assertTrue("begin time is not correct", timeSpan1.getBeginTime().compareTo(new Time(2012, 03, 06, 8, 0)) == 0);
        timeSpan1.changeLengthWith(-20);
        Assert.assertEquals("length has been changed", length, timeSpan1.length());
        Assert.assertTrue("begin time is not correct", timeSpan1.getBeginTime().compareTo(new Time(2012, 03, 06, 8, 0)) == 0);
        timeSpan1.changeLengthWith(60);
        Assert.assertEquals("length has been changed", length + 60, timeSpan1.length());
        Assert.assertTrue("begin time is not correct", timeSpan1.getBeginTime().compareTo(new Time(2012, 03, 06, 8, 0)) == 0);

    }

    @Test
    public void testIsPartOf() {
        /**
         *
         * @param timeSpan
         * @return true if all moments within this time span are included within [timeSpan],
         * otherwise false
         */
        ITimeSpan timeSpan2 = new TimeSpan(new Time(2012, 03, 06, 8, 0), new Time(2012, 03, 06, 9, 30));
        Assert.assertTrue("part of", timeSpan1.isPartOf(timeSpan2));
        timeSpan2 = new TimeSpan(new Time(2012, 03, 06, 8, 0), new Time(2012, 03, 06, 9, 31));
        Assert.assertTrue("part of", timeSpan1.isPartOf(timeSpan2));
        timeSpan2 = new TimeSpan(new Time(2012, 03, 06, 7, 59), new Time(2012, 03, 06, 9, 31));
        Assert.assertTrue("part of", timeSpan1.isPartOf(timeSpan2));
    }

    @Test
    public void testUnionWith() {
        /**
         *
         * @param timeSpan
         * @return if this time span and [timeSpan] are consecutive or possess a
         * common intersection then the smallest time span which includes this time span
         * and [timeSpan] otherwise null will be returned
         */
        ITimeSpan timeSpan2 = new TimeSpan(new Time(2012, 03, 06, 8, 0), new Time(2012, 03, 06, 9, 30));
        ITimeSpan union = timeSpan1.unionWith(timeSpan2);
        Assert.assertTrue(union.getBeginTime().compareTo(timeSpan1.getBeginTime()) == 0);
        Assert.assertTrue(union.getEndTime().compareTo(timeSpan1.getEndTime()) == 0);

        timeSpan2 = new TimeSpan(new Time(2012, 03, 06, 8, 0), new Time(2012, 03, 06, 9, 31));
        union = timeSpan1.unionWith(timeSpan2);
        Assert.assertTrue(union.getBeginTime().compareTo(timeSpan1.getBeginTime()) == 0);
        Assert.assertTrue(union.getEndTime().compareTo(timeSpan2.getEndTime()) == 0);


        timeSpan2 = new TimeSpan(new Time(2012, 03, 06, 7, 59), new Time(2012, 03, 06, 9, 31));
        union = timeSpan1.unionWith(timeSpan2);
        Assert.assertTrue(union.getBeginTime().compareTo(timeSpan2.getBeginTime()) == 0);
        Assert.assertTrue(union.getEndTime().compareTo(timeSpan2.getEndTime()) == 0);

        timeSpan2 = new TimeSpan(new Time(2012, 03, 06, 8, 1), new Time(2012, 03, 06, 9, 31));
        union = timeSpan1.unionWith(timeSpan2);
        Assert.assertTrue(union.getBeginTime().compareTo(timeSpan1.getBeginTime()) == 0);
        Assert.assertTrue(union.getEndTime().compareTo(timeSpan2.getEndTime()) == 0);

        timeSpan2 = new TimeSpan(new Time(2012, 03, 06, 9, 30), new Time(2012, 03, 06, 9, 31));
        union = timeSpan1.unionWith(timeSpan2);
        Assert.assertTrue(union.getBeginTime().compareTo(timeSpan1.getBeginTime()) == 0);
        Assert.assertTrue(union.getEndTime().compareTo(timeSpan2.getEndTime()) == 0);

        timeSpan2 = new TimeSpan(new Time(2012, 03, 06, 9, 31), new Time(2012, 03, 06, 9, 32));
        union = timeSpan1.unionWith(timeSpan2);
        Assert.assertNull(union);
    }

    @Test
    public void testIntersectionWith() {
        /**
         *
         * @param timeSpan
         * @return the largest time span which is part of this time span
         * and [timeSpan] will be returned; if the intersection is empty null will
         * be returned
         */
        ITimeSpan timeSpan2 = new TimeSpan(new Time(2012, 03, 06, 8, 0), new Time(2012, 03, 06, 9, 30));
        ITimeSpan intersection = timeSpan1.intersectionWith(timeSpan2);
        Assert.assertTrue(intersection.getBeginTime().compareTo(timeSpan1.getBeginTime()) == 0);
        Assert.assertTrue(intersection.getEndTime().compareTo(timeSpan1.getEndTime()) == 0);

        timeSpan2 = new TimeSpan(new Time(2012, 03, 06, 8, 0), new Time(2012, 03, 06, 9, 31));
        intersection = timeSpan1.intersectionWith(timeSpan2);
        Assert.assertTrue(intersection.getBeginTime().compareTo(timeSpan1.getBeginTime()) == 0);
        Assert.assertTrue(intersection.getEndTime().compareTo(timeSpan1.getEndTime()) == 0);


        timeSpan2 = new TimeSpan(new Time(2012, 03, 06, 7, 59), new Time(2012, 03, 06, 9, 31));
        intersection = timeSpan1.intersectionWith(timeSpan2);
        Assert.assertTrue(intersection.getBeginTime().compareTo(timeSpan1.getBeginTime()) == 0);
        Assert.assertTrue(intersection.getEndTime().compareTo(timeSpan1.getEndTime()) == 0);


        timeSpan2 = new TimeSpan(new Time(2012, 03, 06, 8, 1), new Time(2012, 03, 06, 9, 31));
        intersection = timeSpan1.intersectionWith(timeSpan2);
        Assert.assertTrue(intersection.getBeginTime().compareTo(timeSpan2.getBeginTime()) == 0);
        Assert.assertTrue(intersection.getEndTime().compareTo(timeSpan1.getEndTime()) == 0);


        timeSpan2 = new TimeSpan(new Time(2012, 03, 06, 9, 30), new Time(2012, 03, 06, 9, 32));
        intersection = timeSpan1.intersectionWith(timeSpan2);
        Assert.assertNull(intersection);
    }
}
