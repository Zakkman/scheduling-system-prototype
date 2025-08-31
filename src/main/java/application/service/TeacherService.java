package application.service;

import application.models.users.Teacher;
import application.repository.users.TeacherRepo;
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
