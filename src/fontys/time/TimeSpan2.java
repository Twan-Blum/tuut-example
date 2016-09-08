/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

/**
 *
 * @author Frank Peeters
 */
public class TimeSpan2 implements ITimeSpan {

    private ITime bt;
    private int length;

    public TimeSpan2(ITime bt, ITime et) {
       if (bt.compareTo(et) >= 0) {
            throw new IllegalArgumentException("begin time "
                    + bt + " must be earlier then end time " + et);
        }
       
        this.bt = bt;
        length = et.difference(bt);
    }

    @Override
    public ITime getBeginTime() {
        return bt;
    }

    @Override
    public ITime getEndTime() {
        return bt.plus(length);
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public void setBeginTime(ITime beginTime) {
        int new_length = length - beginTime.difference(bt);
        if (new_length <=0)  throw new IllegalArgumentException("begin time "
                    + beginTime + " must be earlier then end time " + getEndTime());
        
        length = new_length;
        bt = beginTime;
    }

    @Override
    public void setEndTime(ITime endTime) {
        int new_length = endTime.difference(bt);
        if (new_length <=0)  throw new IllegalArgumentException("begin time "
                    + bt + " must be earlier then end time " + endTime);
        
        length = new_length;
    }

    @Override
    public void move(int minutes) {
        bt = bt.plus(minutes);
    }

    @Override
    public void changeLengthWith(int minutes) {
        if ( length + minutes <= 0) throw new IllegalArgumentException( 
            "adjudstment length leads to non-positive value");

        length += minutes;
    }

    @Override
    public boolean isPartOf(ITimeSpan timeSpan) {
        return (getBeginTime().compareTo(timeSpan.getBeginTime()) >= 0
                && getEndTime().compareTo(timeSpan.getEndTime()) <= 0);
    }

    @Override
    public ITimeSpan unionWith(ITimeSpan timeSpan) {
        ITime et = getEndTime();
        if (bt.compareTo(timeSpan.getEndTime()) > 0 || et.compareTo(timeSpan.getBeginTime()) < 0) {
            return null;
        }
        ITime begintime, endtime;
        if (bt.compareTo(timeSpan.getBeginTime()) < 0) {
            begintime = bt;
        } else {
            begintime = timeSpan.getBeginTime();
        }

        if (getEndTime().compareTo(timeSpan.getEndTime()) > 0) {
            endtime = et;

        } else {
            endtime = timeSpan.getEndTime();
        }

        return new TimeSpan2(begintime, endtime);

    }

    @Override
    public ITimeSpan intersectionWith(ITimeSpan timeSpan) {

        ITime begintime, endtime, et;
        et = getEndTime();
        if (bt.compareTo(timeSpan.getBeginTime()) > 0) {
            begintime = bt;
        } else {
            begintime = timeSpan.getBeginTime();
        }

        if (et.compareTo(timeSpan.getEndTime()) < 0) {
            endtime = et;

        } else {
            endtime = timeSpan.getEndTime();
        }

        if (begintime.compareTo(endtime) >= 0) {
            return null;
        }

        return new TimeSpan2(begintime, endtime);
    }
}
