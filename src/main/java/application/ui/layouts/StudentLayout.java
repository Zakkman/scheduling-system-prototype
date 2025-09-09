package application.ui.layouts;

import application.ui.users.views.student.StudentAddAppointmentsView;
import application.ui.users.views.student.StudentAppointmentsView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.security.AuthenticationContext;

public class StudentLayout extends UserLayout {

    public StudentLayout(AuthenticationContext authenticationContext) {
        super(authenticationContext);
    }

    @Override
    protected Component[] createNavigationButtons() {
        Button appointmentsButton = new Button("Appointments", VaadinIcon.CALENDAR.create());
        appointmentsButton.setWidthFull();
        appointmentsButton.setHeight("150px");
        RouterLink appointmentsLink = new RouterLink("", StudentAppointmentsView.class);
        appointmentsLink.add(appointmentsButton);

        Button addAppointmentsButton = new Button("Add Appointments", VaadinIcon.PLUS_CIRCLE.create());
        addAppointmentsButton.setWidthFull();
        addAppointmentsButton.setHeight("150px");
        RouterLink addAppointmentsLink = new RouterLink("", StudentAddAppointmentsView.class);
        addAppointmentsLink.add(addAppointmentsButton);

        return new Component[] { appointmentsLink, addAppointmentsLink };
    }
}
