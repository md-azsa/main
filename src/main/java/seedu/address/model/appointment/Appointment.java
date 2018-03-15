package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.client.Client;
import seedu.address.model.pet.Pet;
import seedu.address.model.vettechnician.VetTechnician;

/**
 * Represents an Appointment in the application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {

    private final Date date;
    private final Time time;
    private Client client; //dummy variable, class to be created
    private Pet pet; //dummy variable, class to be created
    private VetTechnician vetTech; //dummy variable, class to be created
    private final AppointmentUid uid;

    /**
     * Every field must be present and not null.
     */
    public Appointment(Date date, Time time) {
        requireAllNonNull(date, time);
        this.date = date;
        this.time = time;
        uid = new AppointmentUid();
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }
    
    public void setClient(Client client) {
        this.client = client;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void setVetTech(VetTechnician vetTech) {
        this.vetTech = vetTech;
    }

    public Client getClient() {
        return client;
    }

    public Pet getPet() {
        return pet;
    }

    public VetTechnician getVetTech() {
        return vetTech;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppointment = (Appointment) other;
        return otherAppointment.getDate().equals(this.getDate())
                && otherAppointment.getTime().equals(this.getTime());

    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(date, time, client, pet, vetTech);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        builder.append(" Date: ")
                .append(getDate())
                .append(" Time: ")
                .append(getTime());

        return builder.toString();
    }

    /**
     * @return String of full details of the appointment
     */
    public String toStringFull() {
        final StringBuilder builder = new StringBuilder();

        builder.append(" Date: ")
                .append(getDate())
                .append(" Time: ")
                .append(getTime())
                .append(" Client: ")
                .append(getClient())
                .append(" Pet: ")
                .append(getPet())
                .append(" Vet Tech: ")
                .append(getVetTech());
        return builder.toString();
    }

    /**
     * Returns the uid of the appointment as a {@code AppointmentUid}
     */
    public AppointmentUid getAppointmentUid() {
        return uid;
    }

}
