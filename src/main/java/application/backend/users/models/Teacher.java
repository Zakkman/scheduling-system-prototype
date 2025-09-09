package application.backend.users.models;

import application.backend.common.AbstractEntity;
import application.backend.school.models.Department;
import application.backend.school.models.Section;
import application.backend.school.models.Subject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class Teacher extends AbstractEntity implements SpecificUser<Teacher> {

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "teacher_subjects",
        joinColumns = @JoinColumn(name = "teacher_id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjectsHandled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "teacher_sections",
        joinColumns = @JoinColumn(name = "teacher_id"),
        inverseJoinColumns = @JoinColumn(name = "section_id")
    )
    private Set<Section> sectionsHandled;

    @Lob
    @NotNull
    private byte[] verificationPhoto;
}
