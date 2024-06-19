package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {

    List<Student> getStudents();

    List<Student> getStudentsByName(String name);

    List<Student> getStudentsByAge(Integer age);

    Student getStudentsById(Long id);

    Student addStudent(String name, Integer age);

    Student updateStudent(Long id, String name, Integer age);

    void deleteStudent(Long id);


}
