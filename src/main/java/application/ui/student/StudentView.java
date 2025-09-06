package application.ui.student;

import application.backend.users.services.TeacherService;
import application.ui.layouts.UserLayout;
import application.ui.teacher.TeacherProfileGrid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "student", layout = UserLayout.class)
@RolesAllowed("STUDENT")
public class StudentView extends VerticalLayout {

    private final TeacherProfileGrid teacherProfileGrid;

    public StudentView(TeacherService teacherService) {
        this.teacherProfileGrid = new TeacherProfileGrid(teacherService);

        add(this.teacherProfileGrid);
    }
}
