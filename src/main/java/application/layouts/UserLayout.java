package application.layouts;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import jakarta.annotation.security.RolesAllowed;

@RolesAllowed({"STUDENT", "TEACHER"})
public class UserLayout extends AppLayout {
    public UserLayout() {
        setPrimarySection(Section.NAVBAR);

        H2 title = new H2("Scheduling System");

        Button logoutButton = new Button("Logout");
        //TODO: listener to logout

        HorizontalLayout header = new HorizontalLayout(title, logoutButton);
        header.setWidthFull();
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        addToNavbar(header);
    }

}
