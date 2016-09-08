/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Nico Kuijpers
 */

public class ContactTest {

    private Contact contact;
    private Appointment appointment1, appointment2, appointment3;

    public ContactTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        // Contact Nico
        contact = new Contact("Contact");

        // Appointment from 9.30 till 10.30
        TimeSpan period1 = new TimeSpan(new Time(2014,12,6,9,30),
                                    new Time(2014,12,6,10,30));
        appointment1 = new Appointment("9.30 meeting",period1);

        // Appointment from 10.00 till 11.00
        TimeSpan period2 = new TimeSpan(new Time(2014,12,6,10,0),
                                    new Time(2014,12,6,11,0));
        appointment2 = new Appointment("10.00 meeting",period2);

        // Appointment from 11.00 till 12.00
        TimeSpan period3 = new TimeSpan(new Time(2014,12,6,11,00),
                                    new Time(2014,12,6,12,00));
        appointment3 = new Appointment("11.00 meeting",period3);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testContact() {
        /**
          * a contact with given name with an empty calendar is created
          * @param name
         */
        String name = "Nico";
        Contact nico = new Contact(name);

        // Check if name of contact is correct
        assertEquals("name is incorrect",name,nico.getName());

        // Check if calendar of contact is empty
        int nrAppointments = 0;
        Iterator<Appointment> appointmentIterator = nico.appointments();
        while (appointmentIterator.hasNext()) {
            nrAppointments += 1;
            appointmentIterator.next();
        }
        assertEquals("calendar is not empty",0,nrAppointments);
    }

    @Test
    public void testAddAppointment() {

        /**
         * appointment will be added to the calendar of this contact, but only if
         * this appointment isn't in conflict with another appointment of this contact
         * @param appointment
         * @return true if appointment is added, else false
         */

        // Add first appointment to calendar of contact
        // No conflict
        assertTrue("first appointment",
                   contact.addAppointment(appointment1));

        // Add second appointment to calendar of contact
        // This appointment conflicts with first appointment
        assertFalse("second appointment conflicts with first appointment",
                    contact.addAppointment(appointment2));

        // Add third appointment to calender of contact
        // This appointment has no conflict with first appointment
        assertTrue("third appointment has no conflict with first appointment",
                   contact.addAppointment(appointment3));
    }


    @Test
    public void testRemoveAppointment() {
        /**
         * appointment is not a member (anymore) of the calendar of this contact
         * @param appointment
         */

        // Add appointment to calendar of contact
        // No conflict
        assertTrue("first appointment no conflict",
                   contact.addAppointment(appointment1));

        // Remove appointment from calendar of contact
        contact.removeAppointment(appointment1);

        // Check if appointment is still on calendar of contact
        boolean contactHasAppointment = false;
        Iterator<Appointment> appointmentIterator = contact.appointments();
        while (appointmentIterator.hasNext()) {
            if (appointmentIterator.next() == appointment1) {
                contactHasAppointment = true;
            }
        }
        assertFalse("Contact still has appointment",contactHasAppointment);
    }
}
