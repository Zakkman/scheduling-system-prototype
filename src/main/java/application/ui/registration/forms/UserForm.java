package application.ui.registration.forms;

import application.backend.common.enums.Role;
import application.backend.common.enums.Status;
import application.backend.users.models.User;
import com.vaadin.flow.component.combobox.ComboBox;
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
    ComboBox<Role> role = new ComboBox<>("I am a: ");

    public UserForm() {
        binder.bindInstanceFields(this);

        configurePasswordValidation();

        role.setItems(Role.STUDENT, Role.TEACHER);
        role.setItemLabelGenerator(Role::name);

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.add(
            firstName,
            lastName,
            middleName,
            email,
            password,
            confirmPassword,
            role
        );

        add(formLayout);
    }

    private void configurePasswordValidation() {
        binder.forField(password)
            .asRequired("Password is required")
            .bind(User::getPassword, User::setPassword);

        binder.forField(confirmPassword)
            .asRequired("Confirm password must not be blank")
            .withValidator(value -> password.getValue().equals(value), "Passwords do not match")
            .bind(user -> null, (user, value) -> {});

        password.addValueChangeListener(change ->
            binder.validate()
        );

        confirmPassword.addValueChangeListener(change -> {
            binder.validate();
        });
    }

    public User getUser(Status status) throws ValidationException {
        User user = new User();
        binder.writeBean(user);

        user.setStatus(status);

        return user;
    }

}
