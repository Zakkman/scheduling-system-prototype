package application.backend.school.models;

import application.backend.common.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department extends AbstractEntity {

    @NotBlank
    @Column(unique = true)
    private String name;

}
