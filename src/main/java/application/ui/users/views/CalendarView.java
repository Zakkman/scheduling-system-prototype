package application.ui.users.views;

import application.backend.appointment.services.AppointmentService;
import application.backend.security.CustomUserDetails;
import application.backend.users.models.User;
import application.ui.calendar.Calendar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class CalendarView extends VerticalLayout implements BeforeEnterObserver {

    private final AppointmentService appointmentService;

    public CalendarView(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
        setAlignItems(Alignment.CENTER);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        removeAll();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            User currentUser = ((CustomUserDetails) principal).getUser();
            Calendar calendar = new Calendar(appointmentService, currentUser);

            add(calendar);
        } else {
            event.rerouteTo("login");
            Notification.show("Your session has expired. Please log in again.");
        }
    }
}
