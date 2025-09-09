package application.ui.home.views;

import application.ui.layouts.HomeLayout;
import application.backend.security.RoleChecker;
import com.vaadin.flow.component.AttachEvent;
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
        form.getElement().getThemeList().set("dark", true);

        add(
            form
        );
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        form.getElement().executeJs("this.querySelector('vaadin-text-field').setAttribute('label', 'Email');");
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
