package application.backend.users.models;

import application.backend.common.AbstractEntity;
import application.backend.school.models.Specialization;
import application.backend.school.models.Section;
import application.backend.school.models.Strand;
import application.backend.school.models.Track;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Student extends AbstractEntity implements SpecificUser<Student> {

    @OneToOne
    @NotNull
    @JoinColumn(name = "app_user_id", unique = true)
    private User user;

    @Pattern(regexp = "\\d{12}")
    @Column(unique = true)
    @NotBlank
    private String lrn;

    @ManyToOne
    @NotNull
    private Track track;

    @ManyToOne
    @NotNull
    private Strand strand;

    @ManyToOne
    @NotNull
    private Specialization specialization;

    @ManyToOne
    @NotNull
    private Section section;

    @Lob
    @NotNull
    private byte[] verificationPhoto;
}
