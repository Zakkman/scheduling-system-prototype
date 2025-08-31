package application.backend.security;

import com.vaadin.flow.router.BeforeEnterEvent;
import org.springframework.security.core.context.SecurityContextHolder;

public final class RoleChecker {

    public static void redirectUserIfAuthenticated(BeforeEnterEvent event) {
        boolean isAuthenticated = SecurityContextHolder.getContext().getAuthentication() != null &&
            SecurityContextHolder.getContext().getAuthentication().getPrincipal()
                instanceof org.springframework.security.core.userdetails.UserDetails;

        if (isAuthenticated) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"))) {
                event.forwardTo("teacher");
            } else if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"))) {
                event.forwardTo("student");
            }
        }
    }

}
