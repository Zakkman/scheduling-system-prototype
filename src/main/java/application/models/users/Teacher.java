package application.models.users;

import application.models.AbstractEntity;
import application.models.school.Department;
import application.models.school.Section;
import application.models.school.Subject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Teacher extends AbstractEntity {

    @OneToOne
    @NotNull
    @JoinColumn(name = "app_user_id", unique = true)
    private User user;

    @Column(unique = true)
    @NotBlank
    private String teacherId;

    @ManyToOne
    @NotNull
    private Department department;

    @ManyToMany
    @JoinTable(
        name = "teacher_subjects",
        joinColumns = @JoinColumn(name = "teacher_id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> subjectsHandled;

    @ManyToMany
    @JoinTable(
        name = "teacher_sections",
        joinColumns = @JoinColumn(name = "teacher_id"),
        inverseJoinColumns = @JoinColumn(name = "section_id")
    )
    private List<Section> sectionsHandled;

    @Lob
    @NotNull
    private byte[] verificationPhoto;

}
