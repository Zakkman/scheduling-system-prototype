package application.ui.layouts;

import application.ui.users.views.student.StudentSchedulingView;
import application.ui.users.views.student.StudentAppointmentsView;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.security.AuthenticationContext;

public class StudentLayout extends UserLayout {

    public StudentLayout(AuthenticationContext authenticationContext) {
        super(authenticationContext,
            new RouterLink("", StudentAppointmentsView.class),
            new RouterLink("", StudentSchedulingView.class)
        );
    }
}
