package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.controller.api.StudentRestApi;
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
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/{name}")
    public List<Student> getStudentsByName(String name) {
        return studentService.getStudentsByName(name);
    }

    @GetMapping("/{age}")
    public List<Student> getStudentsByAge(Integer age) {
        return studentService.getStudentsByAge(age);
    }

    @GetMapping("/{id}")
    public Student getStudentsById(Long id) {
        return studentService.getStudentsById(id);
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
