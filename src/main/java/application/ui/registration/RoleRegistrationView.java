package application.ui.registration;

import application.backend.common.enums.Role;
import application.backend.registration.services.RegistrationSessionService;
import application.backend.school.services.SchoolService;
import application.ui.layouts.HomeLayout;
import application.ui.registration.forms.SpecificUserForm;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "register/role", layout = HomeLayout.class)
@AnonymousAllowed
public class RoleRegistrationView extends VerticalLayout implements BeforeEnterObserver, BeforeLeaveObserver {

    private final RegistrationSessionService registrationSessionService;
    private final SpecificUserForm<?> specificUserForm;
    private final Button registerButton;

    public RoleRegistrationView(RegistrationSessionService registrationSessionService,
                                SchoolService schoolService) {
        this.registrationSessionService = registrationSessionService;

        specificUserForm = registrationSessionService.getSpecificUserForm();
        registerButton = new Button("Register");

        configureRegisterButton();

        add(
            specificUserForm,
            registerButton
        );
    }

    private void configureRegisterButton() {
        registerButton.addClickListener(click -> {
            try {
                registrationSessionService.saveSpecificUser(specificUserForm.getSpecificUser());
                UI.getCurrent().navigate("register/verify");
            } catch (ValidationException e) {
                Notification.show("Registration failed: " + e.getMessage());
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (registrationSessionService.getUser() == null) {
            event.rerouteTo("register/user");
            Notification.show("Please complete the first step of registration.");
        }
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
        if (!event.getLocation().getPath().equals("register/verify")) {
            registrationSessionService.clear();
            Notification.show("Registration data has been cleared.");
            event.rerouteTo("register/user");
        }
    }

}
