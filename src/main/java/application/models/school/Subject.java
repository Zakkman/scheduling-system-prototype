package application.models.school;

import application.models.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Subject extends AbstractEntity {

    @NotBlank(message = "Subject name is required")
    @Column(unique = true)
    private String name;

}
