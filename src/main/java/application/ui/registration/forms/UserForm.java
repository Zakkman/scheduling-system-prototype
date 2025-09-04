package application.ui.registration.forms;

import application.backend.common.enums.Role;
import application.backend.common.enums.Status;
import application.backend.users.models.User;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import lombok.Getter;

public class UserForm extends FormLayout {
    @Getter
    Binder<User> binder = new BeanValidationBinder<>(User.class);

    TextField firstName = new TextField("First Name");
    TextField lastName = new TextField("Last Name");
    TextField middleName = new TextField("Middle Name");
    EmailField email = new EmailField("Email");
    PasswordField password = new PasswordField("Password");
    PasswordField confirmPassword = new PasswordField("Confirm Password");

    public UserForm() {
        binder.bindInstanceFields(this);

        binder.forField(password)
            .asRequired("must not be blank") //handles blank cus of @Transient annotation
            .withValidator((value, context) -> {
                if (!confirmPassword.getValue().equals(value)) {
                    return ValidationResult.error("Password do not match");
                }
                return ValidationResult.ok();
            })
            .bind(User::getPassword, User::setPassword);

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.add(
            firstName,
            lastName,
            middleName,
            email,
            password,
            confirmPassword
        );

        add(formLayout);
    }

    public User getUser(Role role, Status status) throws ValidationException {
        User user = new User();
        binder.writeBean(user);

        user.setRole(role);
        user.setStatus(status);

        return user;
    }

}
