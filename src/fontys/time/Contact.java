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
public class Contact {
    
    private String name;
    private ArrayList<Appointment> calendar;
    
    /**
     * a contact with given name with an empty calendar is created
     * @param name 
     */
    public Contact(String name) {
        this.name = name;
        calendar = new ArrayList<>();
    }
    
    /**
     * the name of this contact
     * @return 
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @return an iterator over all the appointments in the calendar (order is
     * not prescribed)
     */
    public Iterator<Appointment> appointments() {
        return calendar.iterator();
    }
    
    /**
     * appointment will be added to the calendar of this contact, but only if 
     * this appointment isn't in conflict with another appointment of this contact
     * @param appointment
     * @return true if appointment is added, else false
     */
    boolean addAppointment(Appointment appointment) {
        for(Appointment app : calendar) {
            if (app.getPeriod().intersectionWith(appointment.getPeriod()) != null )
                return false;
        }
        calendar.add(appointment);
        return true; 
    }

    /**
     * appointment is not a member (anymore) of the calendar of this contact
     * @param appointment 
     */
    void removeAppointment(Appointment appointment) {
        calendar.remove(appointment);
    }
    
}
