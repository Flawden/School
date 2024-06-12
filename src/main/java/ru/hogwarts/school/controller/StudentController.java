package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping
    public HashMap<Long, Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/name")
    public List<Student> getStudentsByName(String name) {
        return studentService.getStudentsByName(name);
    }

    @GetMapping("/age")
    public List<Student> getStudentsByAge(Integer age) {
        return studentService.getStudentsByAge(age);
    }

    @GetMapping("/id")
    public Student getStudentsById(Long id) {
        return studentService.getStudentsById(id);
    }

    @PostMapping
    public Student addStudents(String name, Integer age) {
        return studentService.addStudents(name, age);
    }

    @PatchMapping
    public Student updateStudents(Long id, String name, Integer age) {
        return studentService.updateFaculty(id, name, age);
    }

    @DeleteMapping
    public void deleteStudent(Long id) {
        studentService.deleteStudent(id);
    }

}
