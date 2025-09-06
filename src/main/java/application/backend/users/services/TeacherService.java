package application.backend.users.services;

import application.backend.users.models.Teacher;
import application.backend.users.repositories.TeacherRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepo repo;

    public TeacherService(TeacherRepo repo) {
        this.repo = repo;
    }

    public Teacher save(Teacher teacher) {
        return repo.save(teacher);
    }

    public List<Teacher> findAllTeachers(String nameFilter, String schoolDateFilter) {
        String nameTerm = (nameFilter == null || nameFilter.isBlank())
            ? "" : nameFilter;
        String schoolDataTerm = (schoolDateFilter == null || schoolDateFilter.isBlank())
            ? "" : schoolDateFilter;
        return repo.search(nameTerm, schoolDataTerm);
    }
}
