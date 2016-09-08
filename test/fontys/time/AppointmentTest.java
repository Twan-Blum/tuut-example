/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import java.util.ArrayList;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class AppointmentTest {

    private ITimeSpan period;
    private Appointment appointment;

    public AppointmentTest() {
    }

    @Before
    public void setUp() {
        period = new TimeSpan(new Time(2012, 05, 29, 9, 20),
                            new Time(2012, 05, 29, 9, 35));

        appointment = new Appointment("overleg", period);
    }

    @Test
    public void testGetSubject() {
        /**
         *
         * @return the subject of this appointment
         */
        assertEquals("subject of appointment","overleg",appointment.getSubject());
    }

    @Test
    public void testGetPeriod() {
        /**
          *
          * @return the planned period of the appointment
          */
        assertEquals("period of appointment",period, appointment.getPeriod());
    }

    @Test
    public void testInvitees() {
        /**
         *
         * @return an iterator over all invitees
         */

        Contact frank = new Contact("Frank");
        Contact rick = new Contact("Rick");
        appointment.addContact(rick);
        Iterator<Contact> it = appointment.invitees();
        assertTrue(it.hasNext());
        assertEquals(it.next(),rick);
        assertFalse(it.hasNext());

        appointment.addContact(frank);
        it = appointment.invitees();
        ArrayList<Contact> contacten = new ArrayList<>();
        while (it.hasNext()) {
            contacten.add(it.next());
        }
        assertEquals(2,contacten.size());
        assertTrue(contacten.contains(rick));
        assertTrue(contacten.contains(frank));
    }

    @Test
    public void testAddContact() {
        /**
         * c is added to the invitees of this appointment, but only if
         * this appointment isn't in conflict with the calendar of c; this appointment
         * is added to the calendar of c
         * @param c
         * @return true if adding succeeded, otherwise false
         */
        Contact frank = new Contact("Frank");
        Contact rick = new Contact("Rick");
        assertTrue(appointment.addContact(rick));
        assertTrue(appointment.addContact(frank));
        ITimeSpan period2 = new TimeSpan(new Time(2012, 05, 30, 9, 20),
                                     new Time(2012, 05, 30, 9, 35));
        Appointment appointment2 = new Appointment("blabla", period2);
        assertTrue(appointment2.addContact(rick));
        assertTrue(appointment2.invitees().hasNext());
        assertEquals(appointment2.invitees().next(),rick);
        ITimeSpan period3 = new TimeSpan(new Time(2012, 05, 29, 9, 30),
                                     new Time(2012, 05, 29, 9, 59));
        Appointment appointment3 = new Appointment("blabla2", period3);
        assertFalse(appointment3.addContact(rick));
        assertFalse(appointment3.invitees().hasNext());
    }

    @Test
    public void testRemoveContact() {
        /**
         * c is removed from list of invitees and this appointment is removed from
         * the calendar of c
         * @param c
         */
        Contact frank = new Contact("Frank");
        assertTrue(appointment.addContact(frank));
        appointment.removeContact(frank);

        // Check if Frank is still invited
        boolean frankIsInvited = false;
        Iterator<Contact> contactIterator = appointment.invitees();
        while (contactIterator.hasNext()) {
            if (contactIterator.next() == frank) {
                frankIsInvited = true;
            }
        }
        assertFalse("Frank is still invited to appointment",frankIsInvited);

        // Check if appointment is still on calendar of Frank
        boolean frankHasAppointment = false;
        Iterator<Appointment> appointmentIterator = frank.appointments();
        while (appointmentIterator.hasNext()) {
            if (appointmentIterator.next() == appointment) {
                frankHasAppointment = true;
            }
        }
        assertFalse("Frank still has appointment",frankHasAppointment);
    }
}
