package application.ui.layouts;

import application.ui.views.home.HomeView;
import application.ui.views.login.LoginView;
import application.ui.views.registration.UserRegistrationView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;

@PermitAll
public class HomeLayout extends AppLayout {

    public HomeLayout() {
        setPrimarySection(Section.NAVBAR);

        H3 title = new H3("Scheduling System");

        Tab homeTab = new Tab(new RouterLink("Home", HomeView.class));
        Tab loginTab = new Tab(new RouterLink("Login", LoginView.class));
        Tab registerTab = new Tab(new RouterLink("Register", UserRegistrationView.class));

        HorizontalLayout tabs = new HorizontalLayout(homeTab, loginTab, registerTab);
        HorizontalLayout header = new HorizontalLayout(title, tabs);
        header.setWidthFull();
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        addToNavbar(header);
    }

}
