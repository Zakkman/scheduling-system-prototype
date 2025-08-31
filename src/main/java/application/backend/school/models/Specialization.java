package application.backend.school.models;

import application.backend.common.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Specialization extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @NotNull
    private Track track;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Strand strand;

    @NotBlank
    @Column(unique = true)
    private String name;

}
