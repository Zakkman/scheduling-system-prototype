package application.ui.users.components.scheduling;

import com.vaadin.flow.component.dialog.Dialog;
import lombok.Getter;

@Getter
public class ManageAppointmentDialog extends Dialog {

    private final ManageAppointmentForm form;

    public ManageAppointmentDialog() {
        addClassName("manage-appointment-dialog");
        setSizeFull();

        this.form = new ManageAppointmentForm();

        add(form);
    }
}
