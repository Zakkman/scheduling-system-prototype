package application.ui.layouts;

import application.ui.users.views.teacher.TeacherCalendarView;
import application.ui.users.views.teacher.TeacherSchedulingView;
import application.ui.users.views.teacher.TeacherAppointmentsView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.security.AuthenticationContext;

public class TeacherLayout extends UserLayout {

    public TeacherLayout(AuthenticationContext authenticationContext) {
        super(authenticationContext,
            new RouterLink("", TeacherCalendarView.class),
            new RouterLink("", TeacherAppointmentsView.class),
            new RouterLink("", TeacherSchedulingView.class)
        );
    }
}
