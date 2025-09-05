package application.ui.registration;

import application.backend.common.enums.Role;
import application.backend.email.CodeGenerator;
import application.backend.email.services.EmailService;
import application.backend.registration.services.RegisterService;
import application.backend.registration.services.RegistrationSessionService;
import application.ui.layouts.HomeLayout;
import application.ui.registration.forms.VerifyForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "register/verify", layout = HomeLayout.class)
@AnonymousAllowed
public class VerifyRegistrationView extends AbstractProtectedRegistrationView {

    private RegisterService registerService;
    private final EmailService emailService;
    private static String CODE;

    public VerifyRegistrationView(RegistrationSessionService registrationSessionService,
                                  RegisterService registerService,
                                  EmailService emailService) {
        super(registrationSessionService);
        this.registerService = registerService;
        this.emailService = emailService;
    }

    @Override
    protected void onEnter() {
        CODE = CodeGenerator.getCode();

        String toSend = registrationSessionService.getUser().getEmail();
        String subject = "Verification Code for Automated Consultation Scheduling System";
        String body = "For role " + registrationSessionService.getUser().getRole() +
            " your verification code is: " + CODE;

        emailService.sendEmail(toSend, subject, body);

        add(createContent());
    }

    @Override
    protected Component createContent() {
        VerifyForm verifyForm = new VerifyForm(registrationSessionService.getUser().getEmail());
        Button verifyButton = new Button("Verify");

        configureVerifyButton(verifyForm, verifyButton);

        return new VerticalLayout(verifyForm, verifyButton);
    }

    private void configureVerifyButton(VerifyForm verifyForm, Button verifyButton) {
        verifyButton.addClickListener(click -> {
            boolean codeMatches = verifyForm.compareCode(CODE);
            Role role = registrationSessionService.getRole();

            //TODO: continue this bruh finish this

            if (codeMatches) {
                registerUserBasedOnRole(role);
                Notification.show("Registration successful! You can now log in.");
                UI.getCurrent().navigate("login");
            } else {
                Notification.show("Verification failed. Please check the code and try again.");
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
        return null;
    }
}
