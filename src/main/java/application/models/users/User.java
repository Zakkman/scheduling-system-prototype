package application.models.users;

import application.models.enums.Role;
import application.models.enums.Status;
import application.models.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "scheduling_system_user") // 'user' is a reserved keyword in some SQL databases
public class User extends AbstractEntity {

    @Size(min = 1, max = 50)
    @NotBlank
    private String firstName;

    @Size(min = 1, max = 50)
    @NotBlank
    private String lastName;

    private String middleName;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    @NotBlank
    @Column(unique = true)
    private String email;

    @Transient
    private String password;

    //Handled by the service layer from non-hashed password ^
    @NotNull
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

}