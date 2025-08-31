package application.service;

import application.models.users.Student;
import application.models.users.Teacher;
import application.models.users.User;
import application.models.users.registration.RegistrationData;
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
