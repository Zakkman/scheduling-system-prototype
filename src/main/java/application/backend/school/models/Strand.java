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
public class Strand extends AbstractEntity {

    @ManyToOne
    @NotNull
    private Track track;

    @NotBlank
    @Column(unique = true)
    private String name;

    private String abbreviation;

}
