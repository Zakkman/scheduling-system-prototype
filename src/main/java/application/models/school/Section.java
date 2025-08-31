package application.models.school;

import application.models.AbstractEntity;
import application.models.enums.Strand;
import application.models.enums.Track;
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
public class Section extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Track is required")
    private Track track;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Strand is required")
    private Strand strand;

    @NotBlank(message = "Section name is required")
    @Column(unique = true)
    private String name;
    private String abbreviation;

}
