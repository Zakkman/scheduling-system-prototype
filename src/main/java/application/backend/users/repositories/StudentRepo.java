package application.backend.users.repositories;

import application.backend.users.models.Student;
import application.backend.users.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Long> {

    Optional<Student> findByUser(User user);

    @Query("SELECT DISTINCT s FROM Student s " +
        "WHERE (LOWER(s.user.firstName) LIKE LOWER(CONCAT('%', :nameTerm, '%')) " +
        "OR LOWER(s.user.lastName) LIKE LOWER(CONCAT('%', :nameTerm, '%'))) " +
        "AND (LOWER(s.section.name) LIKE LOWER (CONCAT('%', :schoolDataTerm, '%')) " +
        "OR LOWER(s.track.name) LIKE LOWER (CONCAT('%', :schoolDataTerm, '%')) " +
        "OR LOWER(s.strand.name) LIKE LOWER (CONCAT('%', :schoolDataTerm, '%')))")
    List<Student> search(String nameTerm, String schoolDataTerm);

}
