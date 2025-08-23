package application.views.studentviews;

import application.layouts.UserLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "student", layout = UserLayout.class)
@RolesAllowed("STUDENT")
public class StudentView extends VerticalLayout {

    public StudentView() {
        add(new H1("Student is ME"));
    }

}
