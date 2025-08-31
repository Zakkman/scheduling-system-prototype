package application.backend.registration.services;

import application.backend.registration.models.RegistrationData;
import application.backend.users.models.Student;
import application.backend.users.models.Teacher;
import application.backend.users.models.User;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

@Service
@SessionScope
public class RegistrationSessionService implements Serializable {
    @Getter
    private RegistrationData registrationData = new RegistrationData();

    private static final long serialVersionUID = 1L;

    public User getUser() {
        return registrationData.getUser();
    }

    public void saveUser(User user) {
        registrationData.setUser(user);
    }

    public void saveStudent(Student student) {
        registrationData.setStudent(student);
    }

    public void saveTeacher(Teacher teacher) {
        registrationData.setTeacher(teacher);
    }

    public void clear() {
        registrationData = new RegistrationData();
    }
}
