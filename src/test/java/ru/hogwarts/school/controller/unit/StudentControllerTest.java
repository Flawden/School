package ru.hogwarts.school.controller.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.implementation.StudentServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @Mock
    private StudentServiceImpl studentServiceImpl;

    private static List<Student> students;

    private StudentController studentController;


    @BeforeEach
    public void setUp() {
        studentController = new StudentController(studentServiceImpl);
    }

    @BeforeEach
    public void reposInit() {
        students = new ArrayList<>();
        students.add(new Student(0L, "Валера", 20));
        students.add(new Student(1L, "Сережа", 15));
        students.add(new Student(2L, "Вова", 14));
        students.add(new Student(3L, "Стас", 20));
        students.add(new Student(4L, "Боб", 43));
        students.add(new Student(5L, "Авраам", 56));
    }

    @Test
    public void getStudents() {
        when(studentServiceImpl.getStudents()).thenReturn(students);
        Assertions.assertEquals(students, studentController.getStudents());
    }

    @Test
    public void getStudentsByName() {
        List<Student> testStudents = new ArrayList<>();
        testStudents.add(students.getFirst());
        when(studentServiceImpl.findByNameIgnoreCase("Валера")).thenReturn(testStudents);
        Assertions.assertEquals(testStudents, studentController.findByNameIgnoreCase("Валера"));
    }

    @Test
    public void getStudentsByAge() {
        ArrayList<Student> testStudents = new ArrayList<>();
        testStudents.add(students.getFirst());
        testStudents.add(students.get(3));
        when(studentServiceImpl.getStudentsByAge(20)).thenReturn(testStudents);
        Assertions.assertEquals(testStudents, studentController.getStudentsByAge(20));
    }

    @Test
    public void getStudentById() {
        when(studentServiceImpl.getStudentById(0L)).thenReturn(students.getFirst());
        Assertions.assertEquals(students.getFirst(), studentController.getStudentById(0L));
    }

    @Test
    public void addStudents() {
        Student testStudent = new Student(students.size() + 1L, "Сережа", 25);
        when(studentServiceImpl.addStudent(new Student("Сережа", 25))).thenReturn(testStudent);
        Assertions.assertEquals(testStudent, studentController.addStudent(new Student(students.size() + 1L, "Сережа", 25)));
    }

    @Test
    public void updateStudents() {
        Student testStudent = new Student(0L, "Сережа", 25);
        when(studentServiceImpl.updateStudent(0L, new Student("Сережа", 25))).thenReturn(testStudent);
        Assertions.assertEquals(testStudent, studentController.updateStudents(0L, new Student(0L, "Сережа", 25)));
    }

}
