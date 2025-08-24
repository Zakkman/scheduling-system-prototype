package application.views;

import application.layouts.HomeLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "", layout = HomeLayout.class)
@AnonymousAllowed
public class HomeView extends VerticalLayout implements BeforeEnterObserver {

    public HomeView() {
        add(new H1("Home"));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        boolean isAuthenticated = SecurityContextHolder.getContext().getAuthentication() != null &&
            SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails;

        if (isAuthenticated) {
            // Redirect the user to their respective view if they try to access the HomeView after logging in
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"))) {
                event.forwardTo("teacher");
            } else if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"))) {
                event.forwardTo("student");
            }
        }
    }
}
