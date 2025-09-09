package application.ui.users.views.teacher;

import application.backend.appointment.services.AppointmentService;
import application.backend.security.CustomUserDetails;
import application.backend.users.models.User;
import application.backend.users.services.StudentService;
import application.backend.users.services.TeacherService;
import application.ui.layouts.TeacherLayout;
import application.ui.users.components.grids.AppointmentCardGrid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "teacher/appointments", layout = TeacherLayout.class)
@RolesAllowed("TEACHER")
@PageTitle("My Appointments")
public class TeacherAppointmentsView extends VerticalLayout implements BeforeEnterObserver {

    private final AppointmentCardGrid appointmentCardGrid;

    public TeacherAppointmentsView(AppointmentService appointmentService,
                                   TeacherService teacherService,
                                   StudentService studentService) {
        // The view now passes the services directly to the grid
        this.appointmentCardGrid = new AppointmentCardGrid(teacherService, studentService, appointmentService);
        configureLayout();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            User currentUser = ((CustomUserDetails) principal).getUser();
            // The view only needs to set the user, the grid handles the data retrieval
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