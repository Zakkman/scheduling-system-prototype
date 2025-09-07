package application.ui.views.student;

import application.backend.appointment.models.Appointment;
import application.backend.appointment.models.AppointmentStatus;
import application.backend.appointment.services.AppointmentService;
import application.backend.security.CustomUserDetails;
import application.backend.users.models.Teacher;
import application.backend.users.models.User;
import application.backend.users.services.StudentService;
import application.backend.users.services.TeacherService;
import application.ui.components.custom.SchedulingDialog;
import application.ui.components.forms.scheduling.AppointEvent;
import application.ui.layouts.UserLayout;
import application.ui.components.profiles.TeacherProfile;
import application.ui.components.grids.TeacherProfileGrid;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "student", layout = UserLayout.class)
@RolesAllowed("STUDENT")
public class StudentView extends VerticalLayout {

    //TODO: handle appointment making

    private final AppointmentService appointmentService;
    private final StudentService studentService;
    private final TeacherProfileGrid teacherProfileGrid;
    private final SchedulingDialog schedulingDialog;

    public StudentView(AppointmentService appointmentService,
                       StudentService studentService,
                       TeacherService teacherService) {
        this.studentService = studentService;
        this.appointmentService = appointmentService;
        this.teacherProfileGrid = new TeacherProfileGrid(teacherService);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User appointer = null;

        if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            appointer = userDetails.getUser();
        }

        if (appointer == null) {
            UI.getCurrent().navigate("login");
            Notification.show("Your session has expired. Please log in again.");
        }

        this.schedulingDialog = new SchedulingDialog(appointer, AppointmentStatus.PENDING);

        configureGridSelect();
        configureScheduleDialog();

        add(teacherProfileGrid);

        setAlignItems(Alignment.CENTER);
        setFlexGrow(1, teacherProfileGrid);
        setSizeFull();

    }

    private void configureScheduleDialog() {
        schedulingDialog.addDialogCloseActionListener(close -> handleDialogClose());

        schedulingDialog.getForm().getCancelButton().addClickListener(click -> handleDialogClose());

        schedulingDialog.getForm().addListener(AppointEvent.class, this::handleAppointEvent);
    }

    private void configureGridSelect() {
        teacherProfileGrid.getTeacherGrid().asSingleSelect().addValueChangeListener(
            event -> {
                Teacher selectedTeacher = event.getValue();
                User selectedUser = selectedTeacher.getUser();
                if (selectedTeacher != null) {
                    TeacherProfile profileComponent = new TeacherProfile(selectedTeacher, selectedUser);

                    schedulingDialog.getForm().setUserProfile(profileComponent);

                    schedulingDialog.open();
                }
            });
    }

    private void handleDialogClose() {
        schedulingDialog.getForm().clearFields();
        schedulingDialog.close();
        teacherProfileGrid.getTeacherGrid().asSingleSelect().clear();
    }

    //TODO: finish this bruh

    private void handleAppointEvent(AppointEvent event) {
        try {
            Appointment appointment = event.getAppointmentOrThrow();

        } catch (ValidationException e) {
            Notification.show("Please check the form for errors.");
        }


    }
}