package pro.sky.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.school.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findFacultiesByColorIgnoreCaseOrNameIgnoreCase(String color, String name);
}