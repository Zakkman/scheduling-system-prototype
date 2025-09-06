package application.backend.school.repositories;

import application.backend.school.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepo extends JpaRepository<Subject, Long> {
    Optional<Subject> findByName(String subjectName);
}
