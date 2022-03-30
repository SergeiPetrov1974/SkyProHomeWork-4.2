package pro.sky.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pro.sky.school.exception.*;
import pro.sky.school.model.Student;
import pro.sky.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository repository;
    Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private int count = 0;
    private final Object flag = new Object();

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
        logger.info("Was invoked method to find by find student '{}'", id);
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
        logger.info("Was invoked method to find age student '{}'", age);
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
        logger.info("Was invoked method to find by age1 '{}' or age2 '{}' ignoring case", age1, age2);
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

    public List<String> getAllStudentsNameStartingWithA() {
        logger.info("Was invoked method to find all students starting with A");
        return repository.findAll().stream()
          //      .parallel()
                .map(Student:: getName)
                .map(String:: toUpperCase)
                .filter(it -> it.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }
    public int getStudentAverageAge() {
        logger.info("Was invoked method to find the average student age");
        //return ResponseEntity.ok(repository.getAverageAgeOfAllStudents());
        return (int) repository.findAll().stream()
                .mapToInt(Student:: getAge)
                .average()
                .orElse(0);
    }

    public ResponseEntity<Collection<String>> getNameListByThread() {
        logger.info("Was invoked method to get name list of all students by threads");
        List<String> nameList = repository.findAll().stream()
                .map(Student::getName)
                .collect(Collectors.toList());
        if (nameList.isEmpty()) {
            logger.error("There is no students at all");
            return ResponseEntity.notFound().build();
        }
        System.out.println(nameList.get(0));
        System.out.println(nameList.get(1));
        new Thread(() -> {
            System.out.println(nameList.get(2));
            System.out.println(nameList.get(3));
        }).start();
        new Thread(() -> {
            System.out.println(nameList.get(4));
            System.out.println(nameList.get(5));
        }).start();
        logger.debug("Name list of all students: {}", nameList);
        return ResponseEntity.ok(nameList);
    }

    public ResponseEntity<Collection<String>> getNameListBySyncThread() {
        logger.info("Was invoked method to get name list of all students by synchronized threads");
        List<String> nameList = repository.findAll().stream()
                .map(Student::getName)
                .collect(Collectors.toList());
        if (nameList.isEmpty()) {
            logger.error("There is no students at all");
            return ResponseEntity.notFound().build();
        }

        printElementOfList(nameList);
        printElementOfList(nameList);
        new Thread(() -> {
            printElementOfList(nameList);
            printElementOfList(nameList);
        }).start();
        new Thread(() -> {
            printElementOfList(nameList);
            printElementOfList(nameList);
        }).start();
        logger.debug("Name list of all students: {}", nameList);
        return ResponseEntity.ok(nameList);
    }

    private void printElementOfList(List<String> nameList) {
        System.out.println(nameList.get(count));
        synchronized (flag) {
            count++;
        }
    }
}