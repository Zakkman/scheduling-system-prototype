package application.ui.components.forms.scheduling;

import application.backend.appointment.models.Appointment;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.data.binder.ValidationException;

public class AppointEvent extends ComponentEvent<SchedulingForm> {

    private final Appointment appointment;
    private final ValidationException validationException;

    public AppointEvent(SchedulingForm source, Appointment appointment) {
        super(source, false);
        this.appointment = appointment;
        this.validationException = null;
    }

    public AppointEvent(SchedulingForm source, ValidationException validationException) {
        super(source, false);
        this.appointment = null;
        this.validationException = validationException;
    }

    public Appointment getAppointmentOrThrow() throws ValidationException {
        if (validationException != null) {
            throw this.validationException;
        }
        return appointment;
    }

}