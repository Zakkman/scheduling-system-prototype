package application.backend.users.services;

import application.backend.users.models.Student;
import application.backend.users.models.User;
import application.backend.users.repositories.StudentRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepo repo;

    public StudentService(StudentRepo repo) {
        this.repo = repo;
    }

    public Student saveStudent(Student student) {
        return repo.save(student);
    }

    public Optional<Student> findByUser(User user) {
        return repo.findById(user.getId());
    }
}
