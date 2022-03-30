package pro.sky.school.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pro.sky.school.model.Student;
import pro.sky.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("/{id}")
    public Student getStudentInfo(@PathVariable Long id) {
        return studentService.findStudent(id);
    }

    @PutMapping
    public Student editStudent(@RequestBody Student student) {
        return studentService.editStudent(student);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return HttpStatus.OK;
    }

    @GetMapping // http://localhost:8080/student
    public Collection<Student> getAll() {
        return studentService.getAllStudents();
    }

    @GetMapping("/age/{age}")
    public Collection<Student> findStudentsByAge(@PathVariable int age) {
        return studentService.findByAge(age);
    }

    @GetMapping("/age")
    public Collection<Student> findStudentsByAge(@RequestParam int age1, @RequestParam int age2) {
        return studentService.findByAgeBetween(age1, age2);
    }

    @GetMapping("count-all")
    public ResponseEntity<Long> getAmountOfAllStudents() {
        return studentService.getAmountOfAllStudents();
    }

    @GetMapping("average-age")
    public ResponseEntity<Double> getAverageAgeOfAllStudents() {
        return studentService.getAverageAgeOfAllStudents();
    }

    @GetMapping("last5")
    public ResponseEntity<Collection<Student>> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }

    @GetMapping("/startWithA")
    public List<String> getAllStudentNameStartingWithA() {
        return studentService.getAllStudentsNameStartingWithA();
    }
    @GetMapping("name-list-by-thread")
    public ResponseEntity<Collection<String>> getNameListByThread() {
        return studentService.getNameListByThread();
    }

    @GetMapping("name-list-by-sync-thread")
    public ResponseEntity<Collection<String>> getNameListBySyncThread() {
        return studentService.getNameListBySyncThread();
    }

}