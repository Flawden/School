package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.controller.api.StudentRestApi;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Tag(name="StudentController", description="Предоставляет перечень студентов и операций над ними")
public class StudentController implements StudentRestApi {

    private final StudentService studentService;

    @GetMapping
    @Override
    public List<Student> getStudents() {
        return studentService.getStudents();
    }


    @GetMapping("/name/{name}")
    @Override
    public List<Student> findByNameIgnoreCase(String name) {
        return studentService.findByNameIgnoreCase(name);
    }

    @Override
    @GetMapping("/faculty")
    public Faculty getFacultyOfStudent(Student student) {
        return studentService.getFacultyOfStudent(student);
    }

    @GetMapping("/age/{age}")
    @Override
    public List<Student> getStudentsByAge(Integer age) {
        return studentService.getStudentsByAge(age);
    }

    @GetMapping("/age/between/{min}/{max}")
    @Override
    public List<Student> findByAgeBetween(Integer min, Integer max) {
        return studentService.findByAgeBetween(min,max);
    }

    @GetMapping("/{id}")
    @Override
    public Student getStudentById(Long id) {
        return studentService.getStudentById(id);
    }


    @PostMapping
    public Student addStudent(Student student) {
        return studentService.addStudent(student);
    }

    @PatchMapping("{id}")
    public Student updateStudents(Long id, Student student) {
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("{id}")
    public void deleteStudent(Long id) {
        studentService.deleteStudent(id);
    }

}
