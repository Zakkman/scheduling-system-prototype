package application.ui.users.views;

import application.ui.layouts.UserLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "teacher", layout = UserLayout.class)
@RolesAllowed("TEACHER")
public class TeacherView extends VerticalLayout {

    public TeacherView() {
        add(new H1("Teacher is HERE"));
    }

}
