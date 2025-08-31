package application.backend.registration.models;

import application.backend.users.models.User;
import application.backend.users.models.Student;
import application.backend.users.models.Teacher;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationData {
    private User user;
    private Student student;
    private Teacher teacher;
}
