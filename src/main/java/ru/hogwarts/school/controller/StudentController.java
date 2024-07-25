package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.annotation.LogNameOfRunningMethod;
import ru.hogwarts.school.controller.api.StudentRestApi;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Tag(name = "student", description = "Предоставляет перечень студентов и операций над ними")
@LogNameOfRunningMethod
public class StudentController implements StudentRestApi {

    private final StudentService studentService;

    @GetMapping
    @Override
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @Override
    @GetMapping("/start-with")
    public List<String> getStudentsWhoseNameStartsWith(@PathVariable String startWith) {
        return studentService.getStudentsWhoseNameStartsWith(startWith);
    }

    @GetMapping("/six-students")
    @Override
    public void getSixStudentsByParallel() {
        studentService.getSixStudentsByParallel();
    }

    @GetMapping("/get-six-students-with-synch")
    @Override
    public void getSixStudentsByParallelWithSynchronized() {
        studentService.getSixStudentsByParallelWithSynchronized();
    }

    @GetMapping("/count")
    @Override
    public Integer getCountOfStudents() {
        return studentService.getCountOfStudents();
    }

    @GetMapping("/get-average")
    @Override
    public Double getAverageAgeOfStudents() {
        return studentService.getAverageAgeOfStudents();
    }

    @Override
    public Double getAverageAgeOfStudentsWithStreamApi() {
        return studentService.getAverageAgeOfStudentsWithStreamApi();
    }

    @GetMapping("/get-five")
    @Override
    public List<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }


    @GetMapping("/name/{name}")
    @Override
    public List<Student> findByNameIgnoreCase(@PathVariable("name") String name) {
        return studentService.findByNameIgnoreCase(name);
    }

    @Override
    @GetMapping("/faculty")
    public Faculty getFacultyOfStudent(@RequestBody Student student) {
        return studentService.getFacultyOfStudent(student);
    }

    @GetMapping("/age/{age}")
    @Override
    public List<Student> getStudentsByAge(@PathVariable Integer age) {
        return studentService.getStudentsByAge(age);
    }

    @GetMapping("/age/between")
    @Override
    public List<Student> findByAgeBetween(@RequestParam(defaultValue = "0") Integer min, @RequestParam(defaultValue = "0") Integer max) {
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("/{id}")
    @Override
    public Student getStudentById(@PathVariable("id") Long id) {
        return studentService.getStudentById(id);
    }


    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PatchMapping("{id}")
    public Student updateStudents(@PathVariable("id") Long id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("{id}")
    public void deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
    }

}
