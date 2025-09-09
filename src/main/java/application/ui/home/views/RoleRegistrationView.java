package application.ui.home.views;

import application.backend.common.enums.Role;
import application.backend.registration.services.RegisterService;
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
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "register/role", layout = HomeLayout.class)
@AnonymousAllowed
public class RoleRegistrationView extends AbstractProtectedRegistrationView {

    private final RegisterService registerService;

    public RoleRegistrationView(RegistrationSessionService registrationSessionService, RegisterService registerService) {
        super(registrationSessionService);
        this.registerService = registerService;
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
                // Save the user data to the session
                registrationSessionService.saveSpecificUser(specificUserForm.getSpecificUser());

                String userEmail = registrationSessionService.getUser().getEmail();

                // Check if the email already exists
                if (registerService.isEmailTaken(userEmail)) {
                    Notification.show("This email is already registered. Please use a different one.");
                    return;
                }

                Role role = registrationSessionService.getRole();

                registerUserBasedOnRole(role);
                Notification.show("Registration successful! You can now log in.");
                UI.getCurrent().navigate("login");

            } catch (ValidationException e) {
                Notification.show("Registration failed: " + e.getMessage());
            }
        });
    }

    private void registerUserBasedOnRole(Role role) {
        if (role == Role.STUDENT) {
            registerService.registerStudent(registrationSessionService.getRegistrationData());
        } else if (role == Role.TEACHER) {
            registerService.registerTeacher(registrationSessionService.getRegistrationData());
        }
        //TODO: add admin later on
    }

    @Override
    public String decideNextPath() {
        return "login";
    }
}