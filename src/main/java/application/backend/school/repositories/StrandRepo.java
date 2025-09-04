package application.backend.school.repositories;

import application.backend.school.models.Strand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StrandRepo extends JpaRepository<Strand, Long> {
    Optional<Strand> findByName(String name);
    Optional<Strand> findByAbbreviation(String abbreviation);
}
