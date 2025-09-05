package application.ui.registration;

import application.backend.registration.services.RegistrationSessionService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;

public abstract class AbstractRegistrationView extends VerticalLayout
    implements BeforeEnterObserver, BeforeLeaveObserver {

    protected final RegistrationSessionService registrationSessionService;

    public AbstractRegistrationView(RegistrationSessionService registrationSessionService) {
        this.registrationSessionService = registrationSessionService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        onEnter();
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
        if (!event.getLocation().getPath().equals(decideNextPath())) {
            registrationSessionService.clear();
            Notification.show("Registration data has been cleared.");
        }
    }

    protected abstract String decideNextPath();

    protected abstract void onEnter();

    protected abstract Component createContent();

}
