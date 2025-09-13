package application.ui.users.views.teacher;

import application.backend.appointment.services.AppointmentService;
import application.ui.layouts.TeacherLayout;
import application.ui.users.views.CalendarView;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "teacher/calendar", layout = TeacherLayout.class)
@RolesAllowed("TEACHER")
@PageTitle("My Calendar")
public class TeacherCalendarView extends CalendarView {

    public TeacherCalendarView(AppointmentService appointmentService) {
        super(appointmentService);
    }
}
