package application.ui.users.views.student;

import application.backend.appointment.services.AppointmentService;
import application.ui.layouts.StudentLayout;
import application.ui.users.views.CalendarView;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "student/calendar", layout = StudentLayout.class)
@RolesAllowed("STUDENT")
@PageTitle("My Calendar")
public class StudentCalendarView extends CalendarView {

    public StudentCalendarView(AppointmentService appointmentService) {
        super(appointmentService);
    }
}
