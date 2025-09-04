package application.ui.registration.forms;

import application.backend.school.models.Track;
import application.backend.school.models.Strand;
import application.backend.school.models.Section;
import application.backend.school.models.Specialization;
import application.backend.users.models.Student;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import lombok.Getter;

import java.util.List;


public class StudentForm extends SpecificUserForm<Student> {
    @Getter
    Binder<Student> binder = new BeanValidationBinder<>(Student.class);

    TextField lrn = new TextField("LRN");
    ComboBox<Track> track = new ComboBox<>("Track");
    ComboBox<Strand> strand = new ComboBox<>("Strand");
    ComboBox<Specialization> specialization = new ComboBox<>("Specialization");
    ComboBox<Section> section = new ComboBox<>("Section");
    PhotoUpload verificationPhoto =
        new PhotoUpload("Upload verification photo (with ID in hand)");

    public StudentForm(List<Track> tracks,
                       List<Strand> strands,
                       List<Specialization> specializations,
                       List<Section> sections) {
        binder.bindInstanceFields(this);

        /*TODO: - make the track - strand - specialization - section selection flow process
         */

        track.setItems(tracks);
        track.setItemLabelGenerator(Track::getName);

        strand.setItems(strands);
        strand.setItemLabelGenerator(Strand::getName);

        specialization.setItems(specializations);
        specialization.setItemLabelGenerator(Specialization::getName);

        section.setItems(sections);
        section.setItemLabelGenerator(Section::getName);

        configureSelectionFlow();

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.add(
            lrn,
            track,
            strand,
            specialization,
            section,
            verificationPhoto
        );

        add(formLayout);
    }

    private void configureSelectionFlow() {
        //TODO: make this

    }

    @Override
    public Student getSpecificUser() throws ValidationException {
        Student student = new Student();
        binder.writeBean(student);

        return student;
    }

}
