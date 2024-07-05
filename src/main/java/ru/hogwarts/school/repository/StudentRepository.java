package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByNameIgnoreCase(String name);
    List<Student> findStudentsByAge(Integer age);
    List<Student> findByAgeBetween(Integer min, Integer max);

}
