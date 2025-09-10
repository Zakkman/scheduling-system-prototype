package application.ui.users.views.student;

import application.backend.appointment.services.AppointmentService;
import application.backend.security.CustomUserDetails;
import application.backend.users.models.User;
import application.backend.users.services.StudentService;
import application.backend.users.services.TeacherService;
import application.ui.layouts.StudentLayout;
import application.ui.users.components.grids.AppointmentCardGrid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "student/appointments", layout = StudentLayout.class)
@RolesAllowed("STUDENT")
@PageTitle("My Appointments")
public class StudentAppointmentsView extends VerticalLayout implements BeforeEnterObserver {

    private final AppointmentCardGrid appointmentCardGrid;

    public StudentAppointmentsView(AppointmentService appointmentService,
                                   TeacherService teacherService,
                                   StudentService studentService) {
        this.appointmentCardGrid = new AppointmentCardGrid(teacherService, studentService, appointmentService);

        configureLayout();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            User currentUser = ((CustomUserDetails) principal).getUser();
            this.appointmentCardGrid.setAuthenticatedUser(currentUser);
        } else {
            event.rerouteTo("login");
            Notification.show("Your session has expired. Please log in again.");
        }
    }

    private void configureLayout() {
        add(appointmentCardGrid);
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setFlexGrow(1, appointmentCardGrid);
    }
}