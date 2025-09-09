package application.ui.users.views.student;

import application.backend.appointment.models.Appointment;
import application.backend.appointment.models.AppointmentStatus;
import application.backend.appointment.services.AppointmentService;
import application.backend.users.services.StudentService;
import application.backend.users.services.TeacherService;
import application.backend.users.services.UserService;
import application.ui.layouts.StudentLayout;
import application.ui.users.components.forms.ManageAppointmentForm;
import application.ui.users.views.AppointmentsView;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "student/appointments", layout = StudentLayout.class)
@RolesAllowed("STUDENT")
@PageTitle("My Appointments")
public class StudentAppointmentsView extends AppointmentsView {

    public StudentAppointmentsView(AppointmentService appointmentService,
                                   UserService userService,
                                   TeacherService teacherService,
                                   StudentService studentService) {
        super(appointmentService, userService, teacherService, studentService);
    }

    @Override
    protected void configureAvailableButtons(Appointment appointment) {
        manageAppointmentDialog.getForm().getAcceptButton().setVisible(false);
        manageAppointmentDialog.getForm().getRejectButton().setVisible(false);

        boolean isAppointer = appointment.getAppointer().equals(currentUser);

        manageAppointmentDialog.getForm().getRescheduleButton().setEnabled(false);
        manageAppointmentDialog.getForm().getCancelButton().setEnabled(false);

        if (isAppointer && appointment.getStatus().equals(AppointmentStatus.PENDING)) {
            manageAppointmentDialog.getForm().getRescheduleButton().setEnabled(true);
            manageAppointmentDialog.getForm().getCancelButton().setEnabled(true);
        }
    }

    @Override
    protected void configureManageAppointmentFormEvents() {
        manageAppointmentDialog.getForm().addListener(ManageAppointmentForm.RescheduleEvent.class, this::handleRescheduleEvent);
        manageAppointmentDialog.getForm().addListener(ManageAppointmentForm.CancelEvent.class, this::handleCancelEvent);
        manageAppointmentDialog.getForm().addListener(ManageAppointmentForm.BackEvent.class, this::handleManageAppointmentDialogClose);
    }

}