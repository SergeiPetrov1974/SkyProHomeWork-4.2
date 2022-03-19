package pro.sky.school.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.sky.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int age1, int age2);

    @Query(value = "select count(*) from students", nativeQuery = true)
    Long getAmountOfAllStudents();

    @Query(value = "select avg(age) from students", nativeQuery = true)
    Double getAverageAgeOfAllStudents();

    @Query(value = "select * from students order by id offset (:amount - 5)", nativeQuery = true)
    Collection<Student> getLastFiveStudent(@Param("amount") Long amount);
}