package application.ui.layouts;

import application.ui.home.views.HomeView;
import application.ui.home.views.LoginView;
import application.ui.home.views.UserRegistrationView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;

@PermitAll
public class HomeLayout extends AppLayout {

    public HomeLayout() {
        setPrimarySection(Section.DRAWER);
        addToNavbar(createHeader());
        addToDrawer(createDrawer());
    }

    private Component createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("home-layout-header");
        header.getThemeList().set("dark", true);
        header.setSizeFull();
        header.setWidthFull();
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.START);

        // Add the drawer toggle and title to the left
        header.add(new DrawerToggle(), new H3("Scheduling System"));

        return header;
    }

    private Component createDrawer() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.getThemeList().set("dark", true);
        layout.setPadding(true);
        layout.setSpacing(true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);

        // Create the navigation buttons with icons
        Button homeButton = new Button("Home", VaadinIcon.HOME.create());
        homeButton.setWidthFull();
        homeButton.setHeight("100px");
        RouterLink homeLink = new RouterLink("", HomeView.class);
        homeLink.add(homeButton);

        Button loginButton = new Button("Login", VaadinIcon.SIGN_IN.create());
        loginButton.setWidthFull();
        loginButton.setHeight("100px");
        RouterLink loginLink = new RouterLink("", LoginView.class);
        loginLink.add(loginButton);

        Button registerButton = new Button("Register", VaadinIcon.USER.create());
        registerButton.setWidthFull();
        registerButton.setHeight("100px");
        RouterLink registerLink = new RouterLink("", UserRegistrationView.class);
        registerLink.add(registerButton);

        layout.add(homeLink, loginLink, registerLink);
        return layout;
    }
}
