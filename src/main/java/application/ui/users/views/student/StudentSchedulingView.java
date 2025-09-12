package application.ui.users.views.student;

import application.backend.appointment.services.AppointmentService;
import application.backend.users.models.Teacher;
import application.backend.users.models.User;
import application.backend.users.services.TeacherService;
import application.backend.users.services.UserService;
import application.ui.layouts.StudentLayout;
import application.ui.users.components.grids.ProfileGrid;
import application.ui.users.components.forms.SchedulingForm;
import application.ui.users.components.cards.profiles.TeacherProfile;
import application.ui.users.views.SchedulingView;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "student/add/appointments", layout = StudentLayout.class)
@RolesAllowed("STUDENT")
@PageTitle("Schedule Appointments")
public class StudentSchedulingView extends SchedulingView {

    private final ProfileGrid<Teacher> teacherProfileGrid;

    public StudentSchedulingView(AppointmentService appointmentService,
                                 UserService userService,
                                 TeacherService teacherService) {
        super(appointmentService, userService);

        this.teacherProfileGrid = new ProfileGrid<>(
            "Teachers: ",
            Teacher.class,
            teacherService,
            teacher -> new TeacherProfile(teacher, teacher.getUser()),
            teacherService::search
        );

        add(teacherProfileGrid);
        setFlexGrow(1, teacherProfileGrid);
    }

    @Override
    protected void configureGridSelect() {
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

    @Override
    protected void handleDialogClose(SchedulingForm.CancelEvent event) {
        teacherProfileGrid.getGrid().asSingleSelect().clear();
        schedulingDialog.getForm().clearProfileAndFields();
        schedulingDialog.close();
    }
}