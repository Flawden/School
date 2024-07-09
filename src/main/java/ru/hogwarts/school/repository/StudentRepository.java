package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByNameIgnoreCase(String name);
    List<Student> findStudentsByAge(Integer age);
    List<Student> findByAgeBetween(Integer min, Integer max);

    @Query("SELECT COUNT(Student) FROM Student")
    Integer getCountOfStudents();

    @Query("SELECT AVG(student.age) FROM Student student")
    Integer getAverageAgeOfStudents();

    @Query("SELECT Student FROM Student student ORDER BY student.id DESC LIMIT 5")
    List<Student> getLastFiveStudents();

}
