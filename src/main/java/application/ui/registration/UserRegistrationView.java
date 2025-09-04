package application.ui.registration;

import application.backend.school.services.SchoolService;
import application.ui.registration.forms.StudentForm;
import application.ui.registration.forms.TeacherForm;
import application.ui.registration.forms.UserForm;
import application.ui.layouts.HomeLayout;
import application.backend.common.enums.Role;
import application.backend.common.enums.Status;
import application.backend.users.models.User;
import application.backend.registration.services.RegistrationSessionService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "register/user", layout = HomeLayout.class)
@AnonymousAllowed
public class UserRegistrationView extends VerticalLayout implements BeforeLeaveObserver {

    private final RegistrationSessionService registrationSessionService;
    private final SchoolService schoolService;
    private final UserForm userForm;
    private final ComboBox<Role> roleSelection;
    private final Button nextButton;

    public UserRegistrationView(RegistrationSessionService registrationSessionService,
                                SchoolService schoolService) {
        this.registrationSessionService = registrationSessionService;
        this.schoolService = schoolService;
        this.userForm = new UserForm();
        this.roleSelection = new ComboBox<>("I am a: ");
        this.nextButton = new Button("Next");

        roleSelection.setItems(Role.STUDENT, Role.TEACHER);
        roleSelection.setItemLabelGenerator(Role::name);
        roleSelection.setRequired(true);

        configureNextButton();

        add(
            userForm,
            roleSelection,
            nextButton
        );
    }

    private void configureNextButton() {
        nextButton.addClickListener(click -> {
            try {
                User user = userForm.getUser(roleSelection.getValue(), Status.PENDING);

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
    public void beforeLeave(BeforeLeaveEvent event) {
        if (!event.getLocation().getPath().equals("register/role")) {
            registrationSessionService.clear();
            Notification.show("Registration data has been cleared.");
        }
    }
}


