package application.ui.users.views.student;

import application.backend.appointment.models.Appointment;
import application.backend.appointment.services.AppointmentService;
import application.backend.security.CustomUserDetails;
import application.backend.users.models.User;
import application.backend.users.services.StudentService;
import application.backend.users.services.TeacherService;
import application.ui.layouts.UserLayout;
import application.ui.users.components.grids.AppointmentCardGrid; // Corrected class name
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "student/appointments", layout = UserLayout.class)
@RolesAllowed("STUDENT")
public class StudentAppointmentView extends VerticalLayout implements BeforeEnterObserver {

    private final AppointmentService appointmentService;
    private final AppointmentCardGrid appointmentCardGrid; // Corrected class name

    @Autowired
    public StudentAppointmentView(AppointmentService appointmentService,
                                  TeacherService teacherService,
                                  StudentService studentService) {
        this.appointmentService = appointmentService;

        this.appointmentCardGrid = new AppointmentCardGrid(teacherService, studentService, null);

        configureLayout();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            User currentUser = ((CustomUserDetails) principal).getUser();
            this.appointmentCardGrid.setAuthenticatedUser(currentUser);
            updateAppointmentCards(currentUser);
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

    private void updateAppointmentCards(User currentUser) {
        List<Appointment> appointments = appointmentService.getAppointmentsForUser(currentUser);
        appointmentCardGrid.addAppointmentCards(appointments);
    }
}