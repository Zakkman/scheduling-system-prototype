package application.ui.views.registration;

import application.backend.school.services.SchoolService;
import application.ui.components.forms.registration.StudentForm;
import application.ui.components.forms.registration.TeacherForm;
import application.ui.components.forms.registration.UserForm;
import application.ui.layouts.HomeLayout;
import application.backend.common.enums.Role;
import application.backend.common.enums.Status;
import application.backend.users.models.User;
import application.backend.registration.services.RegistrationSessionService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "register/user", layout = HomeLayout.class)
@AnonymousAllowed
public class UserRegistrationView extends AbstractRegistrationView {

    private final SchoolService schoolService;

    public UserRegistrationView(RegistrationSessionService registrationSessionService,
                                SchoolService schoolService) {
        super(registrationSessionService);
        this.schoolService = schoolService;
    }

    @Override
    protected void onEnter() {
        add(createContent());
    }

    @Override
    protected Component createContent() {
        UserForm userForm = new UserForm();
        Button nextButton = new Button("Next");

        configureNextButton(userForm, nextButton);

        return new VerticalLayout(userForm, nextButton);
    }


    private void configureNextButton(UserForm userForm, Button nextButton) {
        nextButton.addClickListener(click -> {
            try {
                User user = userForm.getUser(Status.PENDING);

                registrationSessionService.saveUser(user);

                if (user.getRole() == Role.STUDENT) {
                    registrationSessionService.setSpecificUserForm(new StudentForm(
                        schoolService.getTracks(),
                        schoolService.getStrands(),
                        schoolService.getSpecializations(),
                        schoolService.getSections()
                    ));
                } else if (user.getRole() == Role.TEACHER) {
                    registrationSessionService.setSpecificUserForm(new TeacherForm(
                        schoolService.getDepartments(),
                        schoolService.getSubjects(),
                        schoolService.getSections()
                    ));
                }

                UI.getCurrent().navigate("register/role");
            } catch (ValidationException e) {
                Notification.show("Please check the form for errors.", 3000, Notification.Position.MIDDLE);
            }
        });
    }

    @Override
    public String decideNextPath() {
        return "register/role";
    }
}


