package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@Tag(name="StudentController", description="Предоставляет перечень студентов и операций над ними")
public class StudentController {

    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/name")
    @Operation(
            summary = "Найти студента по имени",
            description = "Позволяет совершить поиск всех студентов по имени"
    )
    public List<Student> getStudentsByName(String name) {
        return studentService.getStudentsByName(name);
    }

    @GetMapping("/age")
    @Operation(
            summary = "Найти студентов по возрасту",
            description = "Позволяет совершить поиск всех0 студентов по указанному возрасту"
    )
    public List<Student> getStudentsByAge(Integer age) {
        return studentService.getStudentsByAge(age);
    }

    @GetMapping("/id")
    @Operation(
            summary = "Найти студента по Id",
            description = "Позволяет совершить поиск студента по ID"
    )
    public Student getStudentsById(Long id) {
        return studentService.getStudentsById(id);
    }

    @PostMapping
    @Operation(
            summary = "Добавить студента",
            description = "Записать имя человека в студенты школы магии и волшебства"
    )
    public Student addStudents(String name, Integer age) {
        return studentService.addStudents(name, age);
    }

    @PatchMapping
    @Operation(
            summary = "Обновить студента",
            description = "Переписать биографию студента с чистого листа"
    )
    public Student updateStudents(Long id, String name, Integer age) {
        return studentService.updateStudent(id, name, age);
    }

    @DeleteMapping
    @Operation(
            summary = "Удалить студента",
            description = "Стереть имя студента с лица земли и вычеркнуть из учебников истории"
    )
    public void deleteStudent(Long id) {
        studentService.deleteStudent(id);
    }

}
