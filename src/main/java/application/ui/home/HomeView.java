package application.ui.home;

import application.ui.layouts.HomeLayout;
import application.backend.security.RoleChecker;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "", layout = HomeLayout.class)
@AnonymousAllowed
public class HomeView extends VerticalLayout implements BeforeEnterObserver {

    public HomeView() {
        setSizeFull();
        addClassName("home-view");
        add(createHeroSection());
    }

    private Component createHeroSection() {

        VerticalLayout heroSection = new VerticalLayout();
        heroSection.addClassName("hero-section");

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("home-button-layout");

        H1 header = new H1("Welcome to the Automated Student-Teacher " +
            "\n Consultation Scheduling System");
        header.addClassName("home-header");
        Span subHeader = new Span("Schedule and manage consultations with ease!");
        subHeader.addClassName("home-sub-header");
        Button getStartedButton = new Button("Get Started", event -> {
            UI.getCurrent().navigate("register/user");
        });
        getStartedButton.addClassName("home-get-started");
        Button loginButton = new Button("Login", event -> {
            UI.getCurrent().navigate("login");
        });
        loginButton.addClassName("home-login");

        buttonLayout.add(getStartedButton, loginButton);
        heroSection.add(header, subHeader, buttonLayout);

        return heroSection;
    }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        RoleChecker.redirectUserIfAuthenticated(event);
    }
}
