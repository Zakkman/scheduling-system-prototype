package application.ui.views.home.registration;

import application.ui.components.forms.UserForm;
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
    private final UserForm userForm;
    private final ComboBox<Role> roleSelection;
    private final Button nextButton;

    public UserRegistrationView(RegistrationSessionService registrationSessionService) {
        this.registrationSessionService = registrationSessionService;
        this.userForm = new UserForm();
        this.roleSelection = new ComboBox<>("I am a: ");
        this.nextButton = new Button("Next");

        roleSelection.setItems(Role.STUDENT, Role.TEACHER);
        roleSelection.setItemLabelGenerator(Role::name);

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
                    UI.getCurrent().navigate("register/student");
                } else if (user.getRole() == Role.TEACHER) {
                    UI.getCurrent().navigate("register/teacher");
                }
            } catch (ValidationException e) {
                Notification.show("Please check the form for errors.", 3000, Notification.Position.MIDDLE);
            }
        });
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
        if (!event.getLocation().getPath().startsWith("register/")) {
            registrationSessionService.clear();
            Notification.show("Registration data has been cleared.");
        }
    }
}


