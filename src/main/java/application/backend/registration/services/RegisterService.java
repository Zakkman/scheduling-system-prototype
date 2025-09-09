package application.backend.registration.services;

import application.backend.registration.models.RegistrationData;
import application.backend.users.models.Student;
import application.backend.users.models.Teacher;
import application.backend.users.models.User;
import application.backend.users.services.StudentService;
import application.backend.users.services.TeacherService;
import application.backend.users.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {

    private final UserService userService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    public RegisterService(UserService userService,
                           StudentService studentService,
                           TeacherService teacherService) {
        this.userService = userService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @Transactional
    public void registerStudent(RegistrationData registrationData) {
        User savedUser = userService.saveUser(registrationData.getUser());

        Student student = (Student) registrationData.getSpecificUser();
        student.setUser(savedUser);
        studentService.save(student);
    }

    @Transactional
    public void registerTeacher(RegistrationData registrationData) {
        User savedUser = userService.saveUser(registrationData.getUser());

        Teacher teacher = (Teacher) registrationData.getSpecificUser();
        teacher.setUser(savedUser);
        teacherService.save(teacher);
    }

    public boolean isEmailTaken(String userEmail) {
        return userService.isEmailTaken(userEmail);
    }
}
