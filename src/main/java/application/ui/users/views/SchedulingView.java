package application.ui.users.views;

import application.backend.appointment.models.Appointment;
import application.backend.appointment.services.AppointmentService;
import application.backend.security.CustomUserDetails;
import application.backend.users.models.User;
import application.backend.users.services.UserService;
import application.ui.users.components.dialogs.SchedulingDialog;
import application.ui.users.components.forms.SchedulingForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class SchedulingView extends VerticalLayout implements BeforeEnterObserver {

    private final AppointmentService appointmentService;
    private final UserService userService;

    private User currentUser;

    protected SchedulingDialog schedulingDialog;

    public SchedulingView(AppointmentService appointmentService,
                          UserService userService) {
        this.appointmentService = appointmentService;
        this.userService = userService;

        setAlignItems(Alignment.CENTER);
        setSizeFull();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            this.currentUser = ((CustomUserDetails) principal).getUser();

            this.schedulingDialog = new SchedulingDialog(currentUser);

            configureScheduleDialog();
            configureGridSelect();
        } else {
            event.rerouteTo("login");
            Notification.show("Your session has expired. Please log in again.");
        }
    }

    private void configureScheduleDialog() {
        schedulingDialog.getForm().addListener(SchedulingForm.AppointEvent.class, this::handleAppointEvent);
        schedulingDialog.getForm().addListener(SchedulingForm.CancelEvent.class, this::handleDialogClose);
        schedulingDialog.addDialogCloseActionListener(close -> handleDialogClose(null));
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
                handleDialogClose(null);
            } else {
                Notification.show("Error: One of the users could not be found.");
            }

        } catch (ValidationException e) {
            Notification.show("Please check the form for errors.");
        }
    }

    protected abstract void configureGridSelect();

    protected abstract void handleDialogClose(SchedulingForm.CancelEvent event);
}
