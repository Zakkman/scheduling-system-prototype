package application.ui.registration.forms;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class VerifyForm extends FormLayout {

    /*TODO: - put header and instruction on a vert layout (and code input too maybe?)
       - add binder here and expected code field and validate it that way
     */


    private final H2 header;
    private final Text instructions;
    private final TextField codeInput;

    public VerifyForm(String email) {
        header = new H2("Verify");
        instructions = new Text("Enter the 6 digit code sent to your email: " + email);
        codeInput = new TextField();

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.add(
            header,
            instructions,
            codeInput
        );

        add(formLayout);
    }

    public boolean compareCode(String CODE) {
        if (codeInput.getValue().equals(CODE)) {
            return true;
        } else {
            //TODO: fix this
            return false;
        }
    }

}
