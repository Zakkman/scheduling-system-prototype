package application.ui.users.components.dialogs;

import application.backend.users.models.User;
import application.ui.users.components.forms.SchedulingForm;
import com.vaadin.flow.component.dialog.Dialog;
import lombok.Getter;

@Getter
public class SchedulingDialog extends Dialog {

    private final SchedulingForm form;

    public SchedulingDialog(User currentUser) {
        addClassName("scheduling-dialog");
        setSizeFull();

        this.form = new SchedulingForm(currentUser);

        add(form);
    }
}
