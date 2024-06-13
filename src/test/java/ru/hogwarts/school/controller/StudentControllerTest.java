package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    private static HashMap<Long, Student> students;

    private StudentController studentController;

    @BeforeEach
    public void setUp() {
        studentController = new StudentController(studentService);
    }

    @BeforeEach
    public void reposInit() {
        students = new HashMap<>();
        students.put(0L, new Student(0L, "Валера", 20));
        students.put(1L, new Student(1L, "Сережа", 15));
        students.put(2L, new Student(2L, "Вова", 14));
        students.put(3L, new Student(3L, "Стас", 20));
        students.put(4L, new Student(4L, "Боб", 43));
        students.put(5L, new Student(5L, "Авраам", 56));
    }

    @Test
    public void getStudents() {
        when(studentService.getStudents()).thenReturn(students);
        Assertions.assertEquals(students, studentController.getStudents());
    }

    @Test
    public void getStudentsByName() {
        List<Student> testStudents = new ArrayList<>();
        testStudents.add(students.get(0L));
        when(studentService.getStudentsByName("Валера")).thenReturn(testStudents);
        Assertions.assertEquals(testStudents, studentController.getStudentsByName("Валера"));
    }

    @Test
    public void getStudentsByAge() {
        ArrayList<Student> testStudents = new ArrayList<>();
        testStudents.add(students.get(0L));
        testStudents.add(students.get(3L));
        when(studentService.getStudentsByAge(20)).thenReturn(testStudents);
        Assertions.assertEquals(testStudents, studentController.getStudentsByAge(20));
    }

    @Test
    public void getStudentsById() {
        when(studentService.getStudentsById(0L)).thenReturn(students.get(0L));
        Assertions.assertEquals(students.get(0L), studentController.getStudentsById(0L));
    }

    @Test
    public void addStudents() {
        Student testStudent = new Student(students.size() + 1L, "Сережа", 25);
        when(studentService.addStudents("Сережа", 25)).thenReturn(testStudent);
        Assertions.assertEquals(testStudent, studentController.addStudents("Сережа", 25));
    }

    @Test
    public void updateStudents() {
        Student testStudent = new Student(0L, "Сережа", 25);
        when(studentService.updateStudent(0L, "Сережа", 25)).thenReturn(testStudent);
        Assertions.assertEquals(testStudent, studentController.updateStudents(0L, "Сережа", 25));
    }

}
