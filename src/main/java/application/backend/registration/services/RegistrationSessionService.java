package application.backend.registration.services;

import application.backend.common.enums.Role;
import application.backend.registration.models.RegistrationData;
import application.backend.users.models.SpecificUser;
import application.backend.users.models.Student;
import application.backend.users.models.Teacher;
import application.backend.users.models.User;
import application.ui.components.forms.registration.SpecificUserForm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

@Service
@SessionScope
public class RegistrationSessionService implements Serializable {

    @Getter
    private RegistrationData registrationData = new RegistrationData();

    @Getter
    @Setter
    private SpecificUserForm<?> specificUserForm;

    private static final long serialVersionUID = 1L;

    public Role getRole() { return getUser().getRole(); }

    public User getUser() {
        return registrationData.getUser();
    }

    public void saveUser(User user) { registrationData.setUser(user); }

    public void saveSpecificUser(SpecificUser<?> specificUser) {
        if (specificUser instanceof Student) {
            saveStudent((Student) specificUser);
        }else if (specificUser instanceof Teacher) {
            saveTeacher((Teacher) specificUser);
        }
    }

    private void saveStudent(Student student) {
        registrationData.setSpecificUser(student);
    }

    private void saveTeacher(Teacher teacher) {
        registrationData.setSpecificUser(teacher);
    }

    public void clear() {
        registrationData = new RegistrationData();
    }
}
