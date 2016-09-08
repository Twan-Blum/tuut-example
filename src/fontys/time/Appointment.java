/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Frank Peeters
 */
public class Appointment {

    private String subject;
    private ITimeSpan period;
    private ArrayList<Contact> invitees;

    /**
     * creation of an appointment with given subject planned during given period 
     * without any invitee
     * @param subject may be an empty string 
     * @param period 
     */
    public Appointment(String subject, ITimeSpan period) {
        this.subject = subject;
        this.period = period;
        invitees = new ArrayList<>();
    }

    /**
     * 
     * @return the subject of this appointment 
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 
     * @return the planned period of the appointment 
     */
    public ITimeSpan getPeriod() {
        return period;
    }

    /**
     * 
     * @return an iterator over all invitees
     */
    public Iterator<Contact> invitees() {
        return invitees.iterator();
    }

    /**
     * c is added to the invitees of this appointment, but only if 
     * this appointment isn't in conflict with the calendar of c; this appointment
     * is added to the calendar of c 
     * @param c 
     * @return true if adding succeeded, otherwise false
     */
    public boolean addContact(Contact c) {
        if (!invitees.contains(c)) {
            if (c.addAppointment(this)) {
                invitees.add(c);
                return true;
            }
        }
        return false;
    }

    /**
     * c is removed from list of invitees and this appointment is removed from
     * the calendar of c
     * @param c 
     */
    public void removeContact(Contact c) {
        c.removeAppointment(this);
        invitees.remove(c);
    }

}
