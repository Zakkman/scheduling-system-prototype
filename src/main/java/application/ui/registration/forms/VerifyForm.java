package application.ui.registration.forms;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class VerifyForm extends FormLayout {

    /*TODO: - add binder here and expected code field and validate it that way
     */
    private final H2 header;
    private final Text instructions;
    private final TextField codeInput;

    private final Binder<String> binder = new Binder<>();
    private String expectedCode;

    public VerifyForm(String email) {
        header = new H2("Verify");
        instructions = new Text("Enter the 6 digit code sent to your email: " + email);
        codeInput = new TextField();

        binder.forField(codeInput)
            .withValidator(value -> value.equals(expectedCode), "Incorrect entered code")
            .bind(
                value -> codeInput.getValue(),
                (field, value) -> {}
            );

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.add(
            header,
            instructions,
            codeInput
        );

        add(formLayout);
    }

    public boolean compareCode(String CODE) {
        this.expectedCode = CODE;
        return binder.validate().isOk();
    }

}
