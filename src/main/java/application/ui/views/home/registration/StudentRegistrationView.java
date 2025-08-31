package application.ui.views.home.registration;

import application.ui.components.forms.StudentForm;
import application.ui.layouts.HomeLayout;
import application.backend.users.models.Student;
import application.backend.registration.services.RegisterService;
import application.backend.registration.services.RegistrationSessionService;
import application.backend.school.services.SchoolService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "register/student", layout = HomeLayout.class)
@AnonymousAllowed
public class StudentRegistrationView extends VerticalLayout implements BeforeEnterObserver, BeforeLeaveObserver {

    private final RegistrationSessionService registrationSessionService;
    private final RegisterService registerService;
    private final StudentForm studentForm;
    private final Button registerButton;

    public StudentRegistrationView(RegistrationSessionService registrationSessionService,
                                   RegisterService registerService,
                                   SchoolService schoolService) {
        this.registrationSessionService = registrationSessionService;
        this.registerService = registerService;
        this.studentForm = new StudentForm(
            schoolService.getSpecializations(),
            schoolService.getSections());
        this.registerButton = new Button("Register");

        configureRegisterButton();

        add(
            studentForm,
            registerButton
        );
    }

    private void configureRegisterButton() {
        registerButton.addClickListener(click -> {
            try {
                Student student = studentForm.getStudent();

                registrationSessionService.saveStudent(student);

                registerService.registerStudent(registrationSessionService.getRegistrationData());

                Notification.show("Registration successful!");
                UI.getCurrent().navigate("login");
                registrationSessionService.clear();
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
        if (!event.getLocation().getPath().startsWith("register/")) {
            registrationSessionService.clear();
            Notification.show("Registration data has been cleared.");
        }
    }

}
