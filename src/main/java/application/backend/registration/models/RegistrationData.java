package application.backend.registration.models;

import application.backend.users.models.User;
import application.backend.users.models.SpecificUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationData {
    private User user;
    private SpecificUser<?> specificUser;
}
