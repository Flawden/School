package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.controller.api.StudentRestApi;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Tag(name="StudentController", description="Предоставляет перечень студентов и операций над ними")
public class StudentController implements StudentRestApi {

    private final StudentService studentService;
    private final ModelMapper mapper;

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

    @GetMapping("/age/{age}")
    @Override
    public List<Student> getStudentsByAge(Integer age) {
        return studentService.getStudentsByAge(age);
    }

    @GetMapping("/age/between/{min}/{max}")
    @Override
    public List<Student> findByAgeBetween(Integer min, Integer max) {
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("/{id}")
    @Override
    public Student getStudentsById(Long id) {
        return studentService.getStudentsById(id);
    }



    @PostMapping
    public Student addStudent(StudentDTO student) {
        return studentService.addStudent(mapper.map(student, Student.class));
    }

    @PatchMapping("{id}")
    public Student updateStudents(Long id, StudentDTO student) {
        return studentService.updateStudent(id, mapper.map(student, Student.class));
    }

    @DeleteMapping("{id}")
    public void deleteStudent(Long id) {
        studentService.deleteStudent(id);
    }

}
