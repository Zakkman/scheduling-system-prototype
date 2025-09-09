package application.ui.layouts;

import application.ui.users.views.teacher.TeacherAddAppointmentsView;
import application.ui.users.views.teacher.TeacherAppointmentsView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class TeacherLayout extends UserLayout {

    public TeacherLayout(AuthenticationContext authenticationContext) {
        super(authenticationContext);
    }

    @Override
    protected Component[] createNavigationButtons() {
        Button appointmentsButton = new Button("Appointments", VaadinIcon.CALENDAR.create());
        appointmentsButton.setWidthFull();
        appointmentsButton.setHeight("150px");
        RouterLink appointmentsLink = new RouterLink("", TeacherAppointmentsView.class);
        appointmentsLink.add(appointmentsButton);

        Button addAppointmentsButton = new Button("Add Appointments", VaadinIcon.PLUS_CIRCLE.create());
        addAppointmentsButton.setWidthFull();
        addAppointmentsButton.setHeight("150px");
        RouterLink addAppointmentsLink = new RouterLink("", TeacherAddAppointmentsView.class);
        addAppointmentsLink.add(addAppointmentsButton);

        return new Component[] { appointmentsLink, addAppointmentsLink };
    }
}
