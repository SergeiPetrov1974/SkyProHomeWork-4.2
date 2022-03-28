package pro.sky.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.school.exception.*;
import pro.sky.school.model.Faculty;
import pro.sky.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

@Service
public class FacultyService {

    private final FacultyRepository repository;
    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository repository) {
        this.repository = repository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method to create by faculty '{}'", faculty);
        try {
            return repository.save(faculty);
        } catch (RuntimeException e) {
            throw new UnableToCreateException();
        }
    }

    public Faculty findFaculty(long id) {
        logger.info("Was invoked method to find by id '{}'", id);
        Optional<Faculty> optionalFaculty = repository.findById(id);
        if (optionalFaculty.isEmpty()) {
            throw new NotFoundException();
        }
        return optionalFaculty.get();
    }

    public Faculty editFaculty(Faculty faculty) {
        try {
            return repository.save(faculty);
        } catch (RuntimeException e) {
            throw new UnableToUpdateException();
        }
    }

    public void deleteFaculty(long id) {
        logger.info("Was invoked method to delete by id '{}'", id);
        try {
            repository.deleteById(id);
        } catch (RuntimeException e) {
            throw new UnableToDeleteException();
        }
    }

    public Collection<Faculty> getAllFaculties() {
        return repository.findAll();
    }

    public Collection<Faculty> findByColorOrName(String color, String name) {
        if (color == null && name == null) {
            throw new BadRequestException();
        }
        Collection<Faculty> faculties =
                repository.findFacultiesByColorIgnoreCaseOrNameIgnoreCase(color, name);
        if (faculties.isEmpty()) {
            throw new NotFoundException();
        }
        logger.info("Was invoked method to find by color '{}' or name '{}' ignoring case", color, name);
        return faculties;
    }

    public String getTheLongestFacultyName() {
        logger.info("Was invoked method to get the longest faculty name");
        return repository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse(null);
    }

}