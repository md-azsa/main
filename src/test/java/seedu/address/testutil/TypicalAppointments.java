package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;

//@@author md-azsa
/**
 * A utility class containing a list of {@code Appointment} objects
 * to be used in tests.
 */
public class TypicalAppointments {

    public static final Appointment APPOINTMENT_1 = new AppointmentBuilder().withDate("2018-02-01")
            .withTime("14:30")
            .withDuration("60")
            .withDescription("Sterilize Garfield")
            .build();
    public static final Appointment APPOINTMENT_2 = new AppointmentBuilder().withDate("2018-03-02")
            .withTime("16:30")
            .withDuration("45")
            .withDescription("Give Tweety Bird the showering service")
            .build();

    private TypicalAppointments() {}

    //@@author Godxin-test
    /**
     * Returns an {@code AddressBook} with all the typical appointments.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Appointment appointment : getTypicalAppointments()) {
            try {
                ab.scheduleAppointment(appointment);
            } catch (DuplicateAppointmentException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }



    public static List<Appointment> getTypicalAppointments() {
        return new ArrayList<>(Arrays.asList(APPOINTMENT_1, APPOINTMENT_2));
    }
}
