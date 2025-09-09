package application.ui.users.views.teacher;

import application.backend.appointment.models.Appointment;
import application.backend.appointment.models.AppointmentStatus;
import application.backend.appointment.services.AppointmentService;
import application.backend.security.CustomUserDetails;
import application.backend.users.models.Student;
import application.backend.users.models.Teacher;
import application.backend.users.models.User;
import application.backend.users.services.StudentService;
import application.backend.users.services.TeacherService;
import application.backend.users.services.UserService;
import application.ui.layouts.TeacherLayout;
import application.ui.users.components.grids.ProfileGrid;
import application.ui.users.components.scheduling.SchedulingDialog;
import application.ui.users.components.scheduling.SchedulingForm;
import application.ui.users.components.cards.profiles.StudentProfile;
import application.ui.users.components.cards.profiles.TeacherProfile;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "teacher/add/appointments", layout = TeacherLayout.class)
@RolesAllowed("TEACHER")
@PageTitle("Schedule Appointments")
public class TeacherAddAppointmentsView extends VerticalLayout implements BeforeEnterObserver {

    private final AppointmentService appointmentService;
    private final UserService userService;
    private final TeacherService teacherService;
    private final StudentService studentService;

    private ProfileGrid<Teacher> teacherProfileGrid;
    private ProfileGrid<Student> studentProfileGrid;
    private SchedulingDialog schedulingDialog;
    private User appointer;

    public TeacherAddAppointmentsView(UserService userService,
                                      AppointmentService appointmentService,
                                      TeacherService teacherService,
                                      StudentService studentService) {
        this.userService = userService;
        this.appointmentService = appointmentService;
        this.teacherService = teacherService;
        this.studentService = studentService;

        // Initialize both grids
        this.teacherProfileGrid = new ProfileGrid<>(
            "Teachers: ",
            Teacher.class,
            teacherService, teacher -> new TeacherProfile(teacher, teacher.getUser()),
            teacherService::search
        );
        this.studentProfileGrid = new ProfileGrid<>(
            "Students: ",
            Student.class,
            studentService, student -> new StudentProfile(student, student.getUser()),
            studentService::search
        );

        // Configure the grid that will be selected
        configureGridSelect();

        // Create the layout for the two grids
        VerticalLayout gridsLayout = new VerticalLayout(teacherProfileGrid, studentProfileGrid);
        gridsLayout.setSizeFull();
        gridsLayout.setFlexGrow(1, teacherProfileGrid, studentProfileGrid);

        add(gridsLayout);
        gridsLayout.setAlignItems(Alignment.CENTER);
        setAlignItems(Alignment.CENTER);
        setFlexGrow(1, gridsLayout);
        setSizeFull();
        setPadding(false);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            this.appointer = ((CustomUserDetails) principal).getUser();

            // Teachers can schedule appointments for approval or directly as confirmed
            // For this example, let's use CONFIRMED as the default status
            this.schedulingDialog = new SchedulingDialog(appointer, AppointmentStatus.CONFIRMED);

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
        // Teachers can schedule with either other teachers or students
        teacherProfileGrid.getGrid().asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                clearOtherGridSelection(studentProfileGrid.getGrid());
                Teacher selectedTeacher = event.getValue();
                TeacherProfile profileComponent = new TeacherProfile(selectedTeacher, selectedTeacher.getUser());
                schedulingDialog.getForm().setUserProfile(profileComponent);
                schedulingDialog.open();
            }
        });

        studentProfileGrid.getGrid().asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                clearOtherGridSelection(teacherProfileGrid.getGrid());
                Student selectedStudent = event.getValue();
                StudentProfile profileComponent = new StudentProfile(selectedStudent, selectedStudent.getUser());
                schedulingDialog.getForm().setUserProfile(profileComponent);
                schedulingDialog.open();
            }
        });
    }

    private void clearOtherGridSelection(Grid<?> gridToClear) {
        gridToClear.asSingleSelect().clear();
    }

    private void handleAppointEvent(SchedulingForm.AppointEvent event) {
        try {
            Appointment appointment = event.getAppointmentOrThrow();

            // Re-fetch managed entities from the database before saving
            User managedAppointer = userService.findByUser(appointment.getAppointer()).orElse(null);
            User managedAppointee = userService.findByUser(appointment.getAppointee()).orElse(null);

            if (managedAppointer != null && managedAppointee != null) {
                appointment.setAppointer(managedAppointer);
                appointment.setAppointee(managedAppointee);
                appointmentService.saveAppointment(appointment);
                Notification.show("Appointment scheduled successfully!");
                handleDialogClose(null);
            } else {
                Notification.show("Error: One of the users could not be found.");
            }

        } catch (ValidationException e) {
            Notification.show("Please check the form for errors.");
        }
    }

    private void handleDialogClose(SchedulingForm.CancelEvent event) {
        teacherProfileGrid.getGrid().asSingleSelect().clear();
        studentProfileGrid.getGrid().asSingleSelect().clear();
        if (schedulingDialog != null) {
            schedulingDialog.getForm().clearProfileAndFields();
            schedulingDialog.close();
        }
    }
}
