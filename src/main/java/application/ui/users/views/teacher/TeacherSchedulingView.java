package application.ui.users.views.teacher;

import application.backend.appointment.services.AppointmentService;
import application.backend.users.models.Student;
import application.backend.users.models.Teacher;
import application.backend.users.services.StudentService;
import application.backend.users.services.TeacherService;
import application.backend.users.services.UserService;
import application.ui.layouts.TeacherLayout;
import application.ui.users.components.grids.ProfileGrid;
import application.ui.users.components.forms.SchedulingForm;
import application.ui.users.components.cards.profiles.StudentProfile;
import application.ui.users.components.cards.profiles.TeacherProfile;
import application.ui.users.views.SchedulingView;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "teacher/add/appointments", layout = TeacherLayout.class)
@RolesAllowed("TEACHER")
@PageTitle("Schedule Appointments")
public class TeacherSchedulingView extends SchedulingView {

    private final ProfileGrid<Teacher> teacherProfileGrid;
    private final ProfileGrid<Student> studentProfileGrid;

    public TeacherSchedulingView(AppointmentService appointmentService,
                                 UserService userService,
                                 TeacherService teacherService,
                                 StudentService studentService) {
        super(appointmentService, userService);

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

        VerticalLayout gridsLayout = new VerticalLayout(teacherProfileGrid, studentProfileGrid);
        gridsLayout.setSizeFull();
        gridsLayout.setFlexGrow(1, teacherProfileGrid, studentProfileGrid);

        add(gridsLayout);
        gridsLayout.setAlignItems(Alignment.CENTER);
        setFlexGrow(1, gridsLayout);
        setPadding(false);
    }

    @Override
    protected void configureGridSelect() {
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

    @Override
    protected void handleDialogClose(SchedulingForm.CancelEvent event) {
        teacherProfileGrid.getGrid().asSingleSelect().clear();
        studentProfileGrid.getGrid().asSingleSelect().clear();
        if (schedulingDialog != null) {
            schedulingDialog.getForm().clearProfileAndFields();
            schedulingDialog.close();
        }
    }
}
