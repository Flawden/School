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

    private StudentRepository studentRepository;

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getStudentsByName(String name) {
        List<Student> studentsByName = studentRepository.findStudentsByName(name);
        if (!studentsByName.isEmpty()) {
            return studentsByName;
        }
        throw new IllegalArgumentException("Ошибка! Факультета с данным названием не существует");
    }

    public List<Student> getStudentsByAge(Integer age) {
        List<Student> studentsByAge = studentRepository.findStudentsByAge(age);
        if (!studentsByAge.isEmpty()) {
            return studentsByAge;
        }
        throw new IllegalArgumentException("Ошибка! Факультета с данным названием не существует");
    }

    public Student getStudentsById(Long id) {
        Optional<Student> student = studentRepository.findStudentById(id);
        if (student.isEmpty()) {
            throw new IllegalArgumentException("Ошибка! Факультета с данным id не существует");
        }
        return student.get();
    }

    public Student addStudents(String name, Integer age) {
        Student student = new Student(name, age);
        checkStudentToDuplicate(student);
        student = studentRepository.save(student);
        return student;
    }

    private void checkStudentToDuplicate(Student student) {
        for (Student ExistingStudent: students.values()) {
            if (ExistingStudent.getName().equals(student.getName())) {
                throw new IllegalArgumentException("Ошибка! Факультет с данным названием уже существует!");
            }
        }
    }

    public Student updateStudent(Long id, String name, Integer age) {
        Student student = new Student(id, name, age);
        students.put(id, student);
        return students.get(id);
    }

    public void deleteStudent(Long id) {
        students.remove(id);
    }


}
