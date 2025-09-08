package application.ui.home.views;

import application.backend.registration.services.RegistrationSessionService;
import application.ui.home.AbstractProtectedRegistrationView;
import application.ui.layouts.HomeLayout;
import application.ui.home.forms.SpecificUserForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "register/role", layout = HomeLayout.class)
@AnonymousAllowed
public class RoleRegistrationView extends AbstractProtectedRegistrationView {

    public RoleRegistrationView(RegistrationSessionService registrationSessionService) {
        super(registrationSessionService);
    }

    @Override
    protected void onEnter() {
        add(createContent());
    }

    @Override
    protected Component createContent() {
        SpecificUserForm<?> specificUserForm = registrationSessionService.getSpecificUserForm();
        Button registerButton = new Button("Register");

        configureRegisterButton(registerButton, specificUserForm);

        return new VerticalLayout(specificUserForm, registerButton);
    }

    private void configureRegisterButton(Button registerButton, SpecificUserForm<?> specificUserForm) {
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
    public String decideNextPath() {
        return "register/verify";
    }

}
