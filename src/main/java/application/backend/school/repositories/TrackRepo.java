package application.backend.school.repositories;

import application.backend.school.models.Track;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrackRepo extends JpaRepository<Track, Long> {
    Optional<Track> findByName(String academic);
}
