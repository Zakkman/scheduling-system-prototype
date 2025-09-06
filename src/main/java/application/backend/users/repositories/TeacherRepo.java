package application.backend.users.repositories;

import application.backend.users.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeacherRepo extends JpaRepository<Teacher, Long> {
    @Query("SELECT DISTINCT t FROM Teacher t " +
        "LEFT JOIN FETCH t.subjectsHandled sub " +
        "LEFT JOIN FETCH t.sectionsHandled sec " +
        "LEFT JOIN FETCH t.department dep " +
        "WHERE (LOWER(t.user.firstName) LIKE LOWER(CONCAT('%', :nameTerm, '%')) " +
        "OR LOWER(t.user.lastName) LIKE LOWER(CONCAT('%', :nameTerm, '%'))) " +
        "AND (LOWER(dep.name) LIKE LOWER (CONCAT('%', :schoolDataTerm, '%')) " +
        "OR LOWER(sub.name) LIKE LOWER (CONCAT('%', :schoolDataTerm, '%')) " +
        "OR LOWER(sec.name) LIKE LOWER (CONCAT('%', :schoolDataTerm, '%')))")
    List<Teacher> search(@Param("nameTerm") String nameTerm, @Param("schoolDataTerm") String schoolDataTerm);
}
