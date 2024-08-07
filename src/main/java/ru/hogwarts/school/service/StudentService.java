package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {

    List<Student> getStudents();

    List<Student> findByNameIgnoreCase(String name);

    List<Student> getStudentsByAge(Integer age);

    List<Student> findByAgeBetween(Integer min, Integer max);

    Student getStudentById(Long id);

    Student addStudent(Student student);

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);

    Faculty getFacultyOfStudent(Student student);
}
