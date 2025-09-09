package application.ui.users.views;

import application.backend.appointment.models.Appointment;
import application.backend.appointment.services.AppointmentService;
import application.backend.security.CustomUserDetails;
import application.backend.users.models.User;
import application.backend.users.services.UserService;
import application.ui.users.components.dialogs.SchedulingDialog;
import application.ui.users.components.forms.SchedulingForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class SchedulingView extends SchedulableView implements BeforeEnterObserver {

    public SchedulingView(AppointmentService appointmentService,
                          UserService userService) {
        super(appointmentService, userService);

        setAlignItems(Alignment.CENTER);
        setSizeFull();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            currentUser = ((CustomUserDetails) principal).getUser();
            schedulingDialog = new SchedulingDialog(currentUser);

            configureScheduleDialog();
            configureGridSelect();
        } else {
            event.rerouteTo("login");
            Notification.show("Your session has expired. Please log in again.");
        }
    }
}
