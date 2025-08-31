package application.models.users.registration;

import application.models.users.User;
import application.models.users.Student;
import application.models.users.Teacher;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationData {
    private User user;
    private Student student;
    private Teacher teacher;
}
