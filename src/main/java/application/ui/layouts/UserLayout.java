package application.ui.layouts;

import application.ui.users.views.student.StudentSchedulingView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.RolesAllowed;

@RolesAllowed({"STUDENT", "TEACHER"})
public abstract class UserLayout extends AppLayout {

    private final AuthenticationContext authenticationContext;

    public UserLayout(AuthenticationContext authenticationContext,
                      RouterLink appointmentsLink,
                      RouterLink schedulingLink) {
        this.authenticationContext = authenticationContext;

        setPrimarySection(Section.DRAWER);
        addToNavbar(createHeader());

        Component[] navigationButtons = createNavigationButtons(appointmentsLink, schedulingLink);
        addToDrawer(createDrawer(navigationButtons));
    }

    private Component createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("user-layout-header");
        header.getThemeList().set("dark", true);
        header.setWidthFull();
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        // Title and menu toggle on the far left
        HorizontalLayout leftHeader = new HorizontalLayout(new DrawerToggle(), new H4("Scheduling System"));
        leftHeader.setAlignItems(FlexComponent.Alignment.CENTER);
        header.add(leftHeader);

        // Logout button on the far right
        Button logoutButton = new Button("Log Out", event -> authenticationContext.logout());
        header.add(logoutButton);

        return header;
    }

    private Component createDrawer(Component[] navigationButtons) {
        VerticalLayout layout = new VerticalLayout();
        layout.addClassName("user-layout-button-layout");
        layout.setSizeFull();
        layout.getThemeList().set("dark", true);
        layout.setPadding(true);
        layout.setSpacing(true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);

        // Add the navigation buttons provided by the subclass
        for (Component navItem : navigationButtons) {
            layout.add(navItem);
        }

        return layout;
    }

    private Component[] createNavigationButtons(RouterLink appointmentsLink, RouterLink schedulingLink) {
        Button appointmentsButton = new Button("Appointments", VaadinIcon.CALENDAR.create());
        appointmentsButton.addClassName("user-layout-appointments-button");
        appointmentsButton.setWidthFull();
        appointmentsButton.setHeight("150px");
        appointmentsLink.add(appointmentsButton);

        Button addAppointmentsButton = new Button("Add Appointments", VaadinIcon.PLUS_CIRCLE.create());
        addAppointmentsButton.addClassName("user-layout-add-appointments-button");
        addAppointmentsButton.setWidthFull();
        addAppointmentsButton.setHeight("150px");
        schedulingLink.add(addAppointmentsButton);

        return new Component[] { appointmentsLink, schedulingLink };
    }
}
