package application.ui.users.views;

import application.backend.appointment.models.Appointment;
import application.backend.appointment.services.AppointmentService;
import application.backend.users.models.User;
import application.backend.users.services.UserService;
import application.ui.users.components.dialogs.SchedulingDialog;
import application.ui.users.components.forms.SchedulingForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.ValidationException;
import lombok.Getter;

public abstract class SchedulableView extends VerticalLayout {

    protected final AppointmentService appointmentService;
    protected final UserService userService;

    @Getter
    protected SchedulingDialog schedulingDialog;
    protected User currentUser;

    public SchedulableView(AppointmentService appointmentService, UserService userService) {
        this.appointmentService = appointmentService;
        this.userService = userService;
    }

    protected void configureScheduleDialog() {
        schedulingDialog.getForm().addListener(SchedulingForm.AppointEvent.class, this::handleAppointEvent);
        schedulingDialog.getForm().addListener(SchedulingForm.CancelEvent.class, this::handleSchedulingDialogClose);
        schedulingDialog.addDialogCloseActionListener(close -> handleSchedulingDialogClose(null));
    }

    private void handleAppointEvent(SchedulingForm.AppointEvent event) {
        try {
            Appointment appointment = event.getAppointmentOrThrow();

            User managedAppointer = userService.findByUser(appointment.getAppointer()).orElse(null);
            User managedAppointee = userService.findByUser(appointment.getAppointee()).orElse(null);

            if (managedAppointer != null && managedAppointee != null) {
                appointment.setAppointer(managedAppointer);
                appointment.setAppointee(managedAppointee);

                appointmentService.saveAppointment(appointment);

                Notification.show("Appointment scheduled successfully!");
                handleSchedulingDialogClose(null);
            } else {
                Notification.show("Error: One of the users could not be found.");
            }

        } catch (ValidationException e) {
            Notification.show("Please check the form for errors.");
        }
    }

    protected abstract void configureGridSelect();

    protected abstract void handleSchedulingDialogClose(SchedulingForm.CancelEvent event);
}
