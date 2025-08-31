package application.backend.users.services;

import application.backend.users.models.Teacher;
import application.backend.users.repositories.TeacherRepo;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    private final TeacherRepo repo;

    public TeacherService(TeacherRepo repo) {
        this.repo = repo;
    }

    public Teacher save(Teacher teacher) {
        return repo.save(teacher);
    }
}
