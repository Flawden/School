package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.controller.api.StudentRestApi;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
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
    public List<StudentDTO> getStudents() {
        List<Student> students = studentService.getStudents();
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for(Student student: students) {
            studentDTOS.add(mapper.map(student, StudentDTO.class));
        }
        return studentDTOS;
    }

    @GetMapping("/name/{name}")
    @Override
    public List<StudentDTO> findByNameIgnoreCase(String name) {
        List<Student> students = studentService.findByNameIgnoreCase(name);
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for(Student student: students) {
            studentDTOS.add(mapper.map(student, StudentDTO.class));
        }
        return studentDTOS;
    }

    @GetMapping("/age/{age}")
    @Override
    public List<StudentDTO> getStudentsByAge(Integer age) {
        List<Student> students = studentService.getStudentsByAge(age);
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for(Student student: students) {
            studentDTOS.add(mapper.map(student, StudentDTO.class));
        }
        return studentDTOS;
    }

    @GetMapping("/age/between/{min}/{max}")
    @Override
    public List<StudentDTO> findByAgeBetween(Integer min, Integer max) {
        List<Student> students = studentService.findByAgeBetween(min,max);
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for(Student student: students) {
            studentDTOS.add(mapper.map(student, StudentDTO.class));
        }
        return studentDTOS;
    }

    @GetMapping("/{id}")
    @Override
    public StudentDTO getStudentsById(Long id) {
        return mapper.map(studentService.getStudentsById(id), StudentDTO.class);
    }



    @PostMapping
    public StudentDTO addStudent(StudentDTO student) {
        return mapper.map(studentService.addStudent(mapper.map(student, Student.class)), StudentDTO.class);
    }

    @PatchMapping("{id}")
    public StudentDTO updateStudents(Long id, StudentDTO student) {
        return mapper.map(studentService.updateStudent(id, mapper.map(student, Student.class)), StudentDTO.class);
    }

    @DeleteMapping("{id}")
    public void deleteStudent(Long id) {
        studentService.deleteStudent(id);
    }

}
