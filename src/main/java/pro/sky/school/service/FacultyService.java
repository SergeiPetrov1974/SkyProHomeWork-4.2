package pro.sky.school.service;

import org.springframework.stereotype.Service;
import pro.sky.school.exception.*;
import pro.sky.school.model.Faculty;
import pro.sky.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class FacultyService {

    private final FacultyRepository repository;

    public FacultyService(FacultyRepository repository) {
        this.repository = repository;
    }

    public Faculty createFaculty(Faculty faculty) {
        try {
            return repository.save(faculty);
        } catch (RuntimeException e) {
            throw new UnableToCreateException();
        }
    }

    public Faculty findFaculty(long id) {
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
        return faculties;
    }

}