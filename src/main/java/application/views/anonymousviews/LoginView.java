package application.views.anonymousviews;

import application.layouts.HomeLayout;
import application.utils.RoleChecker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "login", layout = HomeLayout.class)
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private LoginForm form = new LoginForm();

    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        form.setAction("login");

        add(
            new H1("Scheduling System"),
            form
        );
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if(event.getLocation()
            .getQueryParameters()
            .getParameters()
            .containsKey("error")) {
            form.setError(true);
        }

        RoleChecker.redirectUserIfAuthenticated(event);
    }

}
