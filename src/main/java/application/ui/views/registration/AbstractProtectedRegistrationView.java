package application.ui.views.registration;

import application.backend.registration.services.RegistrationSessionService;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.BeforeEnterEvent;

public abstract class AbstractProtectedRegistrationView extends AbstractRegistrationView {

    public AbstractProtectedRegistrationView(RegistrationSessionService registrationSessionService) {
        super(registrationSessionService);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (registrationSessionService.getUser() == null) {
            event.rerouteTo("register/user");
            Notification.show("Please complete the first step of registration.");
        } else {
            onEnter();
        }
    }
}
