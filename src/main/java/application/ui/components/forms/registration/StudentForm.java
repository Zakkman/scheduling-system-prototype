package application.ui.components.forms.registration;

import application.backend.school.models.Track;
import application.backend.school.models.Strand;
import application.backend.school.models.Section;
import application.backend.school.models.Specialization;
import application.backend.users.models.Student;
import application.ui.components.custom.PhotoUpload;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;


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

        track.setItems(tracks);
        track.setItemLabelGenerator(Track::getName);

        strand.setEnabled(false);
        specialization.setEnabled(false);
        section.setEnabled(false);

        configureSelectionFlow(strands, specializations, sections);

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

    private void configureSelectionFlow(List<Strand> strands,
                                        List<Specialization> specializations,
                                        List<Section> sections) {
        setUpComboBoxFlow(
            track,
            strand,
            strands,
            Strand::getTrack,
            Strand::getName
        );

        setUpComboBoxFlow(
            strand,
            specialization,
            specializations,
            Specialization::getStrand,
            Specialization::getName
        );

        setUpComboBoxFlow(
            specialization,
            section,
            sections,
            Section::getSpecialization,
            Section::getName
        );
    }

    private <P, T> void setUpComboBoxFlow(
        ComboBox<P> parentBox,
        ComboBox<T> targetBox,
        List<T> targetList,
        Function<T, P> getParent,
        Function<T, String> getLabel
    ) {
        parentBox.addValueChangeListener(change -> {
            P selectedParent = parentBox.getValue();
            if (selectedParent != null) {
                targetBox.clear();
                targetBox.setItems(targetList.stream()
                    .filter(item -> selectedParent.equals(getParent.apply(item)))
                    .toList()
                );
                targetBox.setItemLabelGenerator(getLabel::apply);
                targetBox.setEnabled(true);
            } else {
                targetBox.clear();
                targetBox.setEnabled(false);
            }
        });
    }

    @Override
    public Student getSpecificUser() throws ValidationException {
        Student student = new Student();
        binder.writeBean(student);

        return student;
    }

}
