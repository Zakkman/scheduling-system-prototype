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

        Student student = registrationData.getStudent();
        student.setUser(savedUser);
        studentService.saveStudent(student);
    }

    @Transactional
    public void registerTeacher(RegistrationData registrationData) {
        User savedUser = userService.saveUser(registrationData.getUser());

        Teacher teacher = registrationData.getTeacher();
        teacher.setUser(savedUser);
        teacherService.save(teacher);
    }

}
