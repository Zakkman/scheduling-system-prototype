package application.ui.users.views.teacher;

import application.backend.appointment.models.Appointment;
import application.backend.appointment.models.AppointmentStatus;
import application.backend.appointment.services.AppointmentService;
import application.backend.users.services.StudentService;
import application.backend.users.services.TeacherService;
import application.ui.layouts.TeacherLayout;
import application.ui.users.views.AppointmentsView;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "teacher/appointments", layout = TeacherLayout.class)
@RolesAllowed("TEACHER")
@PageTitle("My Appointments")
public class TeacherAppointmentsView extends AppointmentsView {

    public TeacherAppointmentsView(AppointmentService appointmentService,
                                   TeacherService teacherService,
                                   StudentService studentService) {
        super(appointmentService, teacherService, studentService);
    }

    @Override
    protected void configureAvailableButtons(Appointment appointment) {
        manageAppointmentDialog.getForm().clear();
        manageAppointmentDialog.getForm().addAcceptButton();
        manageAppointmentDialog.getForm().addRejectButton();
        manageAppointmentDialog.getForm().addRescheduleButton();
        manageAppointmentDialog.getForm().addCancelButton();

        boolean isAppointer = appointment.getAppointer().equals(currentUser);
        boolean isPending = appointment.getStatus().equals(AppointmentStatus.PENDING);
        boolean isConfirmed = appointment.getStatus().equals(AppointmentStatus.CONFIRMED);

        manageAppointmentDialog.getForm().getAcceptButton().setEnabled(false);
        manageAppointmentDialog.getForm().getRejectButton().setEnabled(false);
        manageAppointmentDialog.getForm().getRescheduleButton().setEnabled(false);
        manageAppointmentDialog.getForm().getCancelButton().setEnabled(false);

        if ((isAppointer && isPending) || (isAppointer && isConfirmed) || (!isAppointer && isConfirmed)) {
            manageAppointmentDialog.getForm().getRescheduleButton().setEnabled(true);
            manageAppointmentDialog.getForm().getCancelButton().setEnabled(true);
        } else if (!isAppointer && isPending) {
            manageAppointmentDialog.getForm().getAcceptButton().setEnabled(true);
            manageAppointmentDialog.getForm().getRejectButton().setEnabled(true);
            manageAppointmentDialog.getForm().getRescheduleButton().setEnabled(true);
        }
    }

    @Override
    protected void configureManageAppointmentDialog() {

    }
}