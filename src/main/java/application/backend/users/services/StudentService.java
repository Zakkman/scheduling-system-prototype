package application.backend.users.services;

import application.backend.users.models.Student;
import application.backend.users.repositories.StudentRepo;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepo repo;

    public StudentService(StudentRepo repo) {
        this.repo = repo;
    }

    public Student saveStudent(Student student) {
        return repo.save(student);
    }
}
