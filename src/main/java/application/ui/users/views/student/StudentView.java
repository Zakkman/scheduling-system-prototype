package application.ui.users.views.student;

import application.backend.appointment.models.Appointment;
import application.backend.appointment.models.AppointmentStatus;
import application.backend.appointment.services.AppointmentService;
import application.backend.security.CustomUserDetails;
import application.backend.users.models.Teacher;
import application.backend.users.models.User;
import application.backend.users.services.StudentService;
import application.backend.users.services.TeacherService;
import application.ui.users.components.grids.ProfileGrid;
import application.ui.users.components.scheduling.SchedulingDialog;
import application.ui.users.components.scheduling.SchedulingForm;
import application.ui.layouts.UserLayout;
import application.ui.users.components.cards.profiles.TeacherProfile;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "student", layout = UserLayout.class)
@RolesAllowed("STUDENT")
public class StudentView extends VerticalLayout implements BeforeEnterObserver {

    private final AppointmentService appointmentService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    private ProfileGrid<Teacher> teacherProfileGrid;
    private SchedulingDialog schedulingDialog;
    private User appointer;

    public StudentView(AppointmentService appointmentService, StudentService studentService, TeacherService teacherService) {
        this.appointmentService = appointmentService;
        this.studentService = studentService;
        this.teacherService = teacherService;

        // The grid does not depend on the user, so it can be initialized here
        this.teacherProfileGrid = new ProfileGrid<>(
            Teacher.class,
            teacherService, teacher -> new TeacherProfile(teacher, teacher.getUser()),
            teacherService::search
        );
        add(teacherProfileGrid);
        setAlignItems(Alignment.CENTER);
        setFlexGrow(1, teacherProfileGrid);
        setSizeFull();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            this.appointer = ((CustomUserDetails) principal).getUser();

            this.schedulingDialog = new SchedulingDialog(appointer, AppointmentStatus.PENDING);

            configureGridSelect();
            configureScheduleDialog();
        } else {
            event.rerouteTo("login");
            Notification.show("Your session has expired. Please log in again.");
        }
    }

    private void configureScheduleDialog() {
        schedulingDialog.getForm().addListener(SchedulingForm.AppointEvent.class, this::handleAppointEvent);
        schedulingDialog.addDialogCloseActionListener(click -> handleDialogClose(null));
        schedulingDialog.getForm().addListener(SchedulingForm.CancelEvent.class, this::handleDialogClose);
    }

    private void configureGridSelect() {
        teacherProfileGrid.getGrid().asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Teacher selectedTeacher = event.getValue();
                User selectedUser = selectedTeacher.getUser();
                TeacherProfile profileComponent = new TeacherProfile(selectedTeacher, selectedUser);
                schedulingDialog.getForm().setUserProfile(profileComponent);
                schedulingDialog.open();
            }
        });
    }

    private void handleAppointEvent(SchedulingForm.AppointEvent event) {
        try {
            Appointment appointment = event.getAppointmentOrThrow();
            appointmentService.saveAppointment(appointment);
            Notification.show("Appointment scheduled successfully!");
            handleDialogClose(null);
        } catch (ValidationException e) {
            Notification.show("Please check the form for errors.");
        }
    }

    private void handleDialogClose(SchedulingForm.CancelEvent event) {
        teacherProfileGrid.getGrid().asSingleSelect().clear();
        schedulingDialog.getForm().clearProfileAndFields();
        schedulingDialog.close();
    }
}