package application.backend.school.repositories;

import application.backend.school.models.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecializationRepo extends JpaRepository<Specialization, Long> {
    Optional<Specialization> findByName(String name);
    Optional<Specialization> findByAbbreviation(String abbreviation);
}
