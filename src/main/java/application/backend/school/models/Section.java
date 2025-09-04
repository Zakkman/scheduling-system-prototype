package application.backend.school.models;

import application.backend.common.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Section extends AbstractEntity {

    @ManyToOne
    @NotNull
    private Track track;

    @ManyToOne
    @NotNull
    private Strand strand;

    @ManyToOne
    private Specialization specialization;

    @NotBlank
    @Column(unique = true)
    private String name;

}
