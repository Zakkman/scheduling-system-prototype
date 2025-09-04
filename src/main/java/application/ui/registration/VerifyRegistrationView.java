package application.ui.registration;

import application.backend.email.CodeGenerator;
import application.backend.email.services.EmailService;
import application.backend.registration.services.RegisterService;
import application.backend.registration.services.RegistrationSessionService;
import application.ui.layouts.HomeLayout;
import application.ui.registration.forms.VerifyForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "register/verify", layout = HomeLayout.class)
@AnonymousAllowed
public class VerifyRegistrationView extends VerticalLayout implements BeforeEnterObserver, BeforeLeaveObserver {

    private final RegistrationSessionService registrationSessionService;
    private final RegisterService registerService;
    private final EmailService emailService;
    private final VerifyForm verifyForm;
    private final Button verifyButton;

    private final static String CODE = CodeGenerator.getCode();

    public VerifyRegistrationView(RegistrationSessionService registrationSessionService,
                                  RegisterService registerService,
                                  EmailService emailService) {
        this.registrationSessionService = registrationSessionService;
        this.registerService = registerService;
        this.emailService = emailService;
        this.verifyForm = new VerifyForm(registrationSessionService.getUser().getEmail());
        this.verifyButton = new Button("Verify");

        configureVerifyButton();

        add(
            verifyForm,
            verifyButton
        );
    }

    private void configureVerifyButton() {
        verifyButton.addClickListener(click -> {
            boolean codeMatches = verifyForm.compareCode(CODE);

            //TODO: finish this

            if (codeMatches) {

            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (registrationSessionService.getUser() == null) {
            event.rerouteTo("register/user");
            Notification.show("Please complete the first step of registration.");
        }

        String toSend = registrationSessionService.getUser().getEmail();
        String subject = "Verification Code for Automated Consultation Scheduling System";
        String body = "For role " + registrationSessionService.getUser().getRole() +
            " your verification code is: " + CODE;

        emailService.sendEmail(toSend, subject, body);
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
        if (!event.getLocation().getPath().startsWith("register/")) {
            registrationSessionService.clear();
            Notification.show("Registration data has been cleared.");
        }
    }
}
