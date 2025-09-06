package application.backend.school.repositories;

import application.backend.school.models.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SectionRepo extends JpaRepository<Section, Long> {
    Optional<Section> findByName(String sectionName);
}
