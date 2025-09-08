package application.backend.users.services;

import application.backend.users.models.Teacher;
import application.backend.users.models.User;
import application.backend.users.repositories.TeacherRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService extends SpecificUserServiceImpl<Teacher, TeacherRepo> {

    public TeacherService(TeacherRepo repo) {
        super(repo);
    }

    public Optional<Teacher> findByUser(User user) {
        return repo.findByUser(user);
    }

    @Override
    public List<Teacher> search(String nameFilter, String schoolDataFilter) {
        String nameTerm = (nameFilter == null || nameFilter.isBlank())
            ? "" : nameFilter;
        String schoolDataTerm = (schoolDataFilter == null || schoolDataFilter.isBlank())
            ? "" : schoolDataFilter;
        return repo.search(nameTerm, schoolDataTerm);
    }
}
