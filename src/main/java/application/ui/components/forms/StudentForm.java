package application.ui.components.forms;

import application.backend.school.models.Strand;
import application.backend.school.models.Track;
import application.backend.school.models.Section;
import application.backend.school.models.Specialization;
import application.backend.users.models.Student;
import application.ui.components.VerificationPhotoUpload;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import lombok.Getter;

import java.util.List;


public class StudentForm extends FormLayout {
    @Getter
    Binder<Student> binder = new BeanValidationBinder<>(Student.class);

    TextField lrn = new TextField("LRN");
    ComboBox<Track> track = new ComboBox<>("Track");
    ComboBox<Strand> strand = new ComboBox<>("Strand");
    ComboBox<Specialization> specialization = new ComboBox<>("Specialization");
    ComboBox<Section> section = new ComboBox<>("Section");
    VerificationPhotoUpload verificationPhoto =
        new VerificationPhotoUpload("Upload verification photo (with ID in hand)");

    public StudentForm(List<Specialization> specializations, List<Section> sections) {
        binder.bindInstanceFields(this);

        track.setItems(Track.values());
        track.setItemLabelGenerator(Track::name);

        strand.setItems(Strand.values());
        strand.setItemLabelGenerator(Strand::name);

        specialization.setItems(specializations);
        specialization.setItemLabelGenerator(Specialization::getName);

        section.setItems(sections);
        section.setItemLabelGenerator(Section::getName);

        add(
            lrn,
            track,
            strand,
            specialization,
            section,
            verificationPhoto
        );
    }

    public Student getStudent() throws ValidationException {
        Student student = new Student();
        binder.writeBean(student);

        return student;
    }
}
