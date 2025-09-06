package application.backend.school.repositories;

import application.backend.school.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepo extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String departmentName);
}
