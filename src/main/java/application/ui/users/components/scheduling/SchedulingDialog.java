package application.ui.users.components.scheduling;

import application.backend.appointment.models.AppointmentStatus;
import application.backend.users.models.User;
import com.vaadin.flow.component.dialog.Dialog;
import lombok.Getter;

@Getter
public class SchedulingDialog extends Dialog {

    private final SchedulingForm form;

    public SchedulingDialog(User appointer,
                            AppointmentStatus DEFAULT_STATUS) {
        addClassName("scheduling-dialog");
        setSizeFull();

        this.form = new SchedulingForm(appointer, DEFAULT_STATUS);

        add(form);
    }
}
