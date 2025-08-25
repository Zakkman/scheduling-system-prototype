package application.views.anonymousviews;

import application.layouts.HomeLayout;
import application.utils.RoleChecker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "", layout = HomeLayout.class)
@AnonymousAllowed
public class HomeView extends VerticalLayout implements BeforeEnterObserver {

    public HomeView() {
        add(new H1("Home"));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        RoleChecker.redirectUserIfAuthenticated(event);
    }
}
