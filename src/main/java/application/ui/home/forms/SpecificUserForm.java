package application.ui.home.forms;

import application.backend.users.models.SpecificUser;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.data.binder.ValidationException;

public abstract class SpecificUserForm<T extends SpecificUser<?>> extends FormLayout {
    public abstract T getSpecificUser() throws ValidationException;
}
