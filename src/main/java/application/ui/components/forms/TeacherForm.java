package application.ui.components.forms;

import application.backend.school.models.Department;
import application.backend.school.models.Section;
import application.backend.school.models.Subject;
import application.backend.users.models.Teacher;
import application.ui.components.VerificationPhotoUpload;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import lombok.Getter;

import java.util.List;

public class TeacherForm extends FormLayout {
    @Getter
    Binder<Teacher> binder = new BeanValidationBinder<>(Teacher.class);

    TextField teacherId = new TextField("Teacher ID");
    ComboBox<Department> department = new ComboBox<>("Department");
    CheckboxGroup<Subject> subjectsHandled = new CheckboxGroup<>("Subjects Handled");
    CheckboxGroup<Section> sectionsHandled = new CheckboxGroup<>("Sections Handled");
    VerificationPhotoUpload verificationPhoto =
        new VerificationPhotoUpload("Upload verification photo (with ID in hand)");

    public TeacherForm(List<Department> departments, List<Subject> subjects, List<Section> sections) {
        binder.bindInstanceFields(this);

        department.setItems(departments);
        department.setItemLabelGenerator(Department::getName);

        subjectsHandled.setItems(subjects);
        subjectsHandled.setItemLabelGenerator(Subject::getName);

        sectionsHandled.setItems(sections);
        sectionsHandled.setItemLabelGenerator(Section::getName);

        add(
            teacherId,
            department,
            subjectsHandled,
            sectionsHandled,
            verificationPhoto
        );
    }

    public Teacher getTeacher() throws ValidationException {
        Teacher teacher = new Teacher();
        binder.writeBean(teacher);

        return teacher;
    }
}
