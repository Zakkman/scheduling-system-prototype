package application.ui.home.views;

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
        addClassName("home-view");
        add(createHeroSection());
    }

    private Component createHeroSection() {
        VerticalLayout heroSection = new VerticalLayout();
        heroSection.addClassName("hero-section");

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");

        H1 header = new H1("Welcome to the Automated Student-Teacher " +
            "\n Consultation Scheduling System");

        Span subHeader = new Span("Schedule and manage consultations with ease!");

        Button getStartedButton = new Button("Get Started", event -> {
            UI.getCurrent().navigate("register/user");
        });

        Button loginButton = new Button("Login", event -> {
            UI.getCurrent().navigate("login");
        });

        buttonLayout.add(getStartedButton, loginButton);
        heroSection.add(header, subHeader, buttonLayout);

        return heroSection;
    }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        RoleChecker.redirectUserIfAuthenticated(event);
    }
}
