package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    private final StudentRepository studentRepository;

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getStudentsByName(String name) {
        List<Student> studentsByName = studentRepository.findStudentsByName(name);
        if (!studentsByName.isEmpty()) {
            return studentsByName;
        }
        throw new IllegalArgumentException("Ошибка! Студентов с данным именем не найдено.");
    }

    public List<Student> getStudentsByAge(Integer age) {
        List<Student> studentsByAge = studentRepository.findStudentsByAge(age);
        if (!studentsByAge.isEmpty()) {
            return studentsByAge;
        }
        throw new IllegalArgumentException("Ошибка! Студентов с данным возрастом не найдено.");
    }

    public Student getStudentsById(Long id) {
        Optional<Student> student = studentRepository.findStudentById(id);
        if (student.isEmpty()) {
            throw new IllegalArgumentException("Ошибка! Факультета с данным id не найдено");
        }
        return student.get();
    }

    public Student addStudents(String name, Integer age) {
        return studentRepository.save(new Student(name, age));
    }

    public Student updateStudent(Long id, String name, Integer age) {
        Optional<Student> student = studentRepository.findStudentById(id);
        if(student.isEmpty()) {
            throw new IllegalArgumentException("Ошибка! Студента с данным id не найдено.");
        }
        student.get().setName(name);
        student.get().setAge(age);
        return studentRepository.save(student.get());
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }


}
