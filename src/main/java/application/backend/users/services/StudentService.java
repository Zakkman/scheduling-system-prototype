package application.backend.users.services;

import application.backend.users.models.Student;
import application.backend.users.models.User;
import application.backend.users.repositories.StudentRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService extends SpecificUserServiceImpl<Student, StudentRepo> {

    public StudentService(StudentRepo repo) { super(repo); }

    public Optional<Student> findByUser(User user) {
        return repo.findByUser(user);
    }

    @Override
    public List<Student> search(String nameFilter, String schoolDataFilter) {
        String nameTerm = (nameFilter == null || nameFilter.isBlank())
            ? "" : nameFilter;
        String schoolDataTerm = (schoolDataFilter == null || schoolDataFilter.isBlank())
            ? "" : schoolDataFilter;
        return repo.search(nameTerm, schoolDataTerm);
    }
}
