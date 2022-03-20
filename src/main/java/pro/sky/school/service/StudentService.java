package pro.sky.school.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pro.sky.school.exception.*;
import pro.sky.school.model.Student;
import pro.sky.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student createStudent(Student student) {
        try {
            return repository.save(student);
        } catch (RuntimeException e) {
            throw new UnableToCreateException();
        }
    }

    public Student findStudent(long id) {
        Optional<Student> optionalStudent = repository.findById(id);
        if (optionalStudent.isEmpty()) {
            throw new NotFoundException();
        }
        return optionalStudent.get();
    }

    public Student editStudent(Student student) {
        try {
            return repository.save(student);
        } catch (RuntimeException e) {
            throw new UnableToUpdateException();
        }
    }

    public void deleteStudent(long id) {
        try {
            repository.deleteById(id);
        } catch (RuntimeException e) {
            throw new UnableToDeleteException();
        }
    }

    public Collection<Student> getAllStudents() {
        return repository.findAll();
    }

    public Collection<Student> findByAge(int age) {
        return repository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(int age1, int age2) {
        if (age1 < 1 || age2 < 1 || age2 < age1) {
            throw new BadRequestException();
        }
        Collection<Student> students = repository.findByAgeBetween(age1, age2);
        if (students.isEmpty()) {
            throw new NotFoundException();
        }
        return students;
    }

    public ResponseEntity<Long> getAmountOfAllStudents() {
        return ResponseEntity.ok(repository.getAmountOfAllStudents());
    }

    public ResponseEntity<Double> getAverageAgeOfAllStudents() {
        return ResponseEntity.ok(repository.getAverageAgeOfAllStudents());
    }

    public ResponseEntity<Collection<Student>> getLastFiveStudents() {
        Long amount = repository.getAmountOfAllStudents();
        Collection<Student> studentList = repository.getLastFiveStudent(amount);
        if (studentList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentList);
    }

}