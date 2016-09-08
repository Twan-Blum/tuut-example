/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.Rule;

public class TimeSpanTest {
//@Rule public org.junit.rules.ExpectedException exception = org.junit.rules.ExpectedException.none();
  
    private ITimeSpan ts_from_8_0_to_9_30;

    @Before
    public void setUp() {
        ts_from_8_0_to_9_30 = new TimeSpan(new Time(2012, 03, 06, 8, 0), new Time(2012, 03, 06, 9, 30));
    }

    @Test
    public void testGetBeginTime() {

        Assert.assertTrue("begintime", (new Time(2012, 3, 6, 8, 0)).compareTo(ts_from_8_0_to_9_30.getBeginTime()) == 0);
    }

    @Test
    public void testGetEndTime() {

        Assert.assertTrue("begintime", (new Time(2012, 03, 06, 9, 30)).compareTo(ts_from_8_0_to_9_30.getEndTime()) == 0);
    }

    @Test
    public void testLength() {

        Assert.assertEquals("length", 90, ts_from_8_0_to_9_30.length());
    }

    @Test
    public void testSetBeginTime() {
        /**
         * beginTime will be the new begin time of this period
         *
         * @param beginTime must be earlier than the current end time of this
         * period
         */
        try {
            ts_from_8_0_to_9_30.setBeginTime(new Time(2012, 03, 06, 9, 40));
            fail("begintime is not earlier");
        } catch (IllegalArgumentException exc) {
        }
        try {
            ts_from_8_0_to_9_30.setBeginTime(ts_from_8_0_to_9_30.getEndTime());
            fail("begintime is not earlier");
        } catch (IllegalArgumentException exc) {
        }

        ts_from_8_0_to_9_30.setBeginTime(ts_from_8_0_to_9_30.getBeginTime().plus(60));
        Assert.assertEquals("begintime plus 60", 30, ts_from_8_0_to_9_30.length());
    }

    @Test
    public void testSetEndTime() {
        /**
         * endTime will be the new end time of this period
         *
         * @param beginTime must be later than the current begin time of this
         * period
         */
        try {
            ts_from_8_0_to_9_30.setEndTime(new Time(2012, 03, 05, 8, 0));
            fail("end time is not later");
        } catch (IllegalArgumentException exc) {
        }
        try {
            ts_from_8_0_to_9_30.setEndTime(ts_from_8_0_to_9_30.getBeginTime());
            fail("end time is not later");
        } catch (IllegalArgumentException exc) {
        }

        ts_from_8_0_to_9_30.setEndTime(ts_from_8_0_to_9_30.getEndTime().plus(-30));
        Assert.assertEquals("endtime minus 30", 60, ts_from_8_0_to_9_30.length());

    }

    @Test
    public void testMove() {
        /**
         * the begin and end time of this period will be moved up both with
         * [minutes] minutes
         *
         * @param minutes (a negative value is allowed)
         */
//        long length = ts_from_8_0_to_9_30.length();
//        ts_from_8_0_to_9_30.move(20);
//        Assert.assertEquals("length has been changed", length, ts_from_8_0_to_9_30.length());
//        Assert.assertTrue("begin time is not correct", ts_from_8_0_to_9_30.getBeginTime().compareTo(new Time(2012, 03, 06, 8, 20)) == 0);
//        ts_from_8_0_to_9_30.move(-20);
//        Assert.assertEquals("length has been changed", length, ts_from_8_0_to_9_30.length());
//        Assert.assertTrue("begin time is not correct", ts_from_8_0_to_9_30.getBeginTime().compareTo(new Time(2012, 03, 06, 8, 0)) == 0);
//        ts_from_8_0_to_9_30.move(90);
//        Assert.assertEquals("length has been changed", length, ts_from_8_0_to_9_30.length());
//        Assert.assertTrue("begin time is not correct", ts_from_8_0_to_9_30.getBeginTime().compareTo(new Time(2012, 03, 06, 9, 30)) == 0);

    }

    @Test
    public void testChangeLengthWith() {

        /**
         * the end time of this period will be moved up with [minutes] minutes
         *
         * @param minutes minutes > -(length of this period)
         */
        try {
            ts_from_8_0_to_9_30.changeLengthWith((int) -ts_from_8_0_to_9_30.length());
            fail("final length must be positive");
        } catch (IllegalArgumentException exc) {
        }
        long length = ts_from_8_0_to_9_30.length();
        ts_from_8_0_to_9_30.changeLengthWith(20);
        Assert.assertEquals("length has been changed", length + 20, ts_from_8_0_to_9_30.length());
        Assert.assertTrue("begin time is not correct", ts_from_8_0_to_9_30.getBeginTime().compareTo(new Time(2012, 03, 06, 8, 0)) == 0);
        ts_from_8_0_to_9_30.changeLengthWith(-20);
        Assert.assertEquals("length has been changed", length, ts_from_8_0_to_9_30.length());
        Assert.assertTrue("begin time is not correct", ts_from_8_0_to_9_30.getBeginTime().compareTo(new Time(2012, 03, 06, 8, 0)) == 0);
        ts_from_8_0_to_9_30.changeLengthWith(60);
        Assert.assertEquals("length has been changed", length + 60, ts_from_8_0_to_9_30.length());
        Assert.assertTrue("begin time is not correct", ts_from_8_0_to_9_30.getBeginTime().compareTo(new Time(2012, 03, 06, 8, 0)) == 0);

    }

    @Test
    public void testIsPartOf() {
        /**
         *
         * @param timeSpan
         * @return true if all moments within this time span are included within
         * [timeSpan], otherwise false
         */
        ITimeSpan timeSpan2 = new TimeSpan(new Time(2012, 03, 06, 8, 0), new Time(2012, 03, 06, 9, 30));
        Assert.assertTrue("part of", ts_from_8_0_to_9_30.isPartOf(timeSpan2));
        timeSpan2 = new TimeSpan(new Time(2012, 03, 06, 8, 0), new Time(2012, 03, 06, 9, 31));
        Assert.assertTrue("part of", ts_from_8_0_to_9_30.isPartOf(timeSpan2));
        timeSpan2 = new TimeSpan(new Time(2012, 03, 06, 7, 59), new Time(2012, 03, 06, 9, 31));
        Assert.assertTrue("part of", ts_from_8_0_to_9_30.isPartOf(timeSpan2));
    }

    @Test
    public void testUnionWith() {
        /**
         *
         * @param timeSpan
         * @return if this time span and [timeSpan] are consecutive or possess a
         * common intersection then the smallest time span which includes this
         * time span and [timeSpan] otherwise null will be returned
         */

        ITime b = ts_from_8_0_to_9_30.getBeginTime();
        ITime e = ts_from_8_0_to_9_30.getEndTime();
        ITime before_b = new Time(2012, 3, 6, 7, 58);
        ITime justbefore_b = new Time(2012, 3, 6, 7, 59);
        ITime justbefore_e = new Time(2012, 3, 6, 9, 29);
        ITime after_e = new Time(2012, 3, 6, 9, 31);

        ITimeSpan ts2 = new TimeSpan(before_b, justbefore_b);
        ITimeSpan union = ts_from_8_0_to_9_30.unionWith(ts2);
        assertNull(union);

        ts2 = new TimeSpan(before_b, b);
        union = ts_from_8_0_to_9_30.unionWith(ts2);
        assertTrue(union.getBeginTime().compareTo(before_b) == 0);
        assertTrue(union.getEndTime().compareTo(e) == 0);

        ts2 = new TimeSpan(before_b, justbefore_e);
        union = ts_from_8_0_to_9_30.unionWith(ts2);
        assertTrue(union.getBeginTime().compareTo(before_b) == 0);
        assertTrue(union.getEndTime().compareTo(e) == 0);

        ts2 = new TimeSpan(before_b, e);
        union = ts_from_8_0_to_9_30.unionWith(ts2);
        assertTrue(union.getBeginTime().compareTo(before_b) == 0);
        assertTrue(union.getEndTime().compareTo(e) == 0);

        ts2 = new TimeSpan(before_b, after_e);
        union = ts_from_8_0_to_9_30.unionWith(ts2);
        assertTrue(union.getBeginTime().compareTo(before_b) == 0);
        assertTrue(union.getEndTime().compareTo(after_e) == 0);

        ts2 = new TimeSpan(b, justbefore_e);
        union = ts_from_8_0_to_9_30.unionWith(ts2);
        assertTrue(union.getBeginTime().compareTo(b) == 0);
        assertTrue(union.getEndTime().compareTo(e) == 0);

        ts2 = new TimeSpan(b, e);
        union = ts_from_8_0_to_9_30.unionWith(ts2);
        assertTrue(union.getBeginTime().compareTo(b) == 0);
        assertTrue(union.getEndTime().compareTo(e) == 0);

        ts2 = new TimeSpan(b, after_e);
        union = ts_from_8_0_to_9_30.unionWith(ts2);
        assertTrue(union.getBeginTime().compareTo(b) == 0);
        assertTrue(union.getEndTime().compareTo(after_e) == 0);

        ts2 = new TimeSpan(justbefore_e, e);
        union = ts_from_8_0_to_9_30.unionWith(ts2);
        assertTrue(union.getBeginTime().compareTo(b) == 0);
        assertTrue(union.getEndTime().compareTo(e) == 0);

        ts2 = new TimeSpan(justbefore_e, after_e);
        union = ts_from_8_0_to_9_30.unionWith(ts2);
        assertTrue(union.getBeginTime().compareTo(b) == 0);
        assertTrue(union.getEndTime().compareTo(after_e) == 0);

        ts2 = new TimeSpan(e, after_e);
        union = ts_from_8_0_to_9_30.unionWith(ts2);
        assertTrue(union.getBeginTime().compareTo(b) == 0);
        assertTrue(union.getEndTime().compareTo(after_e) == 0);

        ts2 = new TimeSpan(after_e, new Time(2012, 3, 6, 9, 50));
        union = ts_from_8_0_to_9_30.unionWith(ts2);
        assertNull(union);


    }

    @Test
    public void testIntersectionWith() {
        /**
         *
         * @param timeSpan
         * @return the largest period which is part of this time span and
         * [timeSpan] will be returned; if the intersection is empty null will
         * be returned
         */
        ITimeSpan timeSpan2 = new TimeSpan(new Time(2012, 03, 06, 8, 0), new Time(2012, 03, 06, 9, 30));
        ITimeSpan intersection = ts_from_8_0_to_9_30.intersectionWith(timeSpan2);
        Assert.assertTrue(intersection.getBeginTime().compareTo(ts_from_8_0_to_9_30.getBeginTime()) == 0);
        Assert.assertTrue(intersection.getEndTime().compareTo(ts_from_8_0_to_9_30.getEndTime()) == 0);

        timeSpan2 = new TimeSpan(new Time(2012, 03, 06, 8, 0), new Time(2012, 03, 06, 9, 31));
        intersection = ts_from_8_0_to_9_30.intersectionWith(timeSpan2);
        Assert.assertTrue(intersection.getBeginTime().compareTo(ts_from_8_0_to_9_30.getBeginTime()) == 0);
        Assert.assertTrue(intersection.getEndTime().compareTo(ts_from_8_0_to_9_30.getEndTime()) == 0);

        timeSpan2 = new TimeSpan(new Time(2012, 03, 06, 7, 59), new Time(2012, 03, 06, 9, 31));
        intersection = ts_from_8_0_to_9_30.intersectionWith(timeSpan2);
        Assert.assertTrue(intersection.getBeginTime().compareTo(ts_from_8_0_to_9_30.getBeginTime()) == 0);
        Assert.assertTrue(intersection.getEndTime().compareTo(ts_from_8_0_to_9_30.getEndTime()) == 0);

        timeSpan2 = new TimeSpan(new Time(2012, 03, 06, 8, 1), new Time(2012, 03, 06, 9, 31));
        intersection = ts_from_8_0_to_9_30.intersectionWith(timeSpan2);
        Assert.assertTrue(intersection.getBeginTime().compareTo(timeSpan2.getBeginTime()) == 0);
        Assert.assertTrue(intersection.getEndTime().compareTo(ts_from_8_0_to_9_30.getEndTime()) == 0);

        timeSpan2 = new TimeSpan(new Time(2012, 03, 06, 9, 30), new Time(2012, 03, 06, 9, 32));
        intersection = ts_from_8_0_to_9_30.intersectionWith(timeSpan2);
        Assert.assertNull(intersection);
    }
}
