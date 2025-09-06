package application.ui.views.student;

import application.backend.users.models.Teacher;
import application.backend.users.services.TeacherService;
import application.ui.components.forms.scheduling.SchedulingForm;
import application.ui.layouts.UserLayout;
import application.ui.components.profiles.TeacherProfile;
import application.ui.components.grids.TeacherProfileGrid;
import com.vaadin.flow.component.dialog.Dialog; // Import the Dialog component
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "student", layout = UserLayout.class)
@RolesAllowed("STUDENT")
public class StudentView extends VerticalLayout {

    //TODO: handle appointment making

    private final TeacherProfileGrid teacherProfileGrid;
    private final SchedulingForm schedulingForm;
    private final Dialog schedulingDialog; // Add a field for the dialog

    public StudentView(TeacherService teacherService) {
        this.teacherProfileGrid = new TeacherProfileGrid(teacherService);
        this.schedulingForm = new SchedulingForm();
        this.schedulingDialog = new Dialog(); // Initialize the dialog

        schedulingDialog.add(schedulingForm);

        schedulingDialog.addDialogCloseActionListener(close -> {
            teacherProfileGrid.getTeacherGrid().asSingleSelect().clear();
        });

        schedulingForm.getCancelButton().addClickListener(click -> {
            schedulingDialog.close();
        });

        configureGridSelect();

        add(teacherProfileGrid);
        setSizeFull();
    }

    private void configureGridSelect() {
        teacherProfileGrid.getTeacherGrid().asSingleSelect().addValueChangeListener(
            event -> {
                Teacher selectedTeacher = event.getValue();
                if (selectedTeacher != null) {
                    TeacherProfile profileComponent = new TeacherProfile(selectedTeacher);

                    schedulingForm.setUserProfile(profileComponent);

                    schedulingDialog.open();
                }
            });
    }
}