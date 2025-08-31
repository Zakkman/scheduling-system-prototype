package application.ui.views.home.registration;

import application.ui.components.forms.TeacherForm;
import application.ui.layouts.HomeLayout;
import application.backend.users.models.Teacher;
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

@Route(value = "register/teacher", layout = HomeLayout.class)
@AnonymousAllowed
public class TeacherRegistrationView extends VerticalLayout implements BeforeEnterObserver, BeforeLeaveObserver {

    private final RegistrationSessionService registrationSessionService;
    private final RegisterService registerService;
    private final TeacherForm teacherForm;
    private final Button registerButton;

    public TeacherRegistrationView(RegistrationSessionService registrationSessionService,
                                   RegisterService registerService,
                                   SchoolService schoolService) {
        this.registrationSessionService = registrationSessionService;
        this.registerService = registerService;
        this.teacherForm = new TeacherForm(
            schoolService.getDepartments(),
            schoolService.getSubjects(),
            schoolService.getSections()
        );
        this.registerButton = new Button("Register");

        configureRegisterButton();

        add(
            teacherForm,
            registerButton
        );
    }

    private void configureRegisterButton() {
        registerButton.addClickListener(click -> {
            try {
                Teacher teacher = teacherForm.getTeacher();

                registrationSessionService.saveTeacher(teacher);

                registerService.registerTeacher(registrationSessionService.getRegistrationData());

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
