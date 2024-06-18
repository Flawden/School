package ru.hogwarts.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class StudentServiceTest {

    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    private static HashMap<Long, Student> students;

    @BeforeEach
    public void setUp() {
        studentService = new StudentService(studentRepository);
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
        Assertions.assertEquals(students, studentService.getStudents());
    }

    @Test
    public void getStudentsByName() {
        Assertions.assertEquals(students.get(0L), studentService.getStudentsByName(students.get(0L).getName()).getFirst());
    }

    @Test
    public void getStudentsByNameWithException() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> studentService.getStudentsByName("Абвывав"));
        Assertions.assertEquals("Ошибка! Факультета с данным названием не существует", exception.getMessage());
    }

    @Test
    public void getStudentsByAge() {
        ArrayList<Student> testList = new ArrayList<>();
        testList.add(students.get(0L));
        testList.add(students.get(3L));
        Assertions.assertEquals(testList, studentService.getStudentsByAge(20));
    }

    @Test
    public void getStudentsByAgeWithException() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> studentService.getStudentsByAge(999));
        Assertions.assertEquals("Ошибка! Факультета с данным названием не существует", exception.getMessage());
    }

    @Test
    public void getStudentsById() {
        Assertions.assertEquals(students.get(0L), studentService.getStudentsById(0L));
    }

    @Test
    public void getStudentsByIdWithException() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> studentService.getStudentsById(students.size() + 1L));
        Assertions.assertEquals("Ошибка! Факультета с данным id не существует", exception.getMessage());
    }

    @Test
    public void addStudent() {
        Student testStudent = new Student(students.size() + 1L, "Глеб", 12);
        Assertions.assertEquals(testStudent, studentService.addStudents("Глеб", 12));
    }

    @Test
    public void updateStudent() {
        Student testStudent = new Student(1L, "Стеблехвост", 21);
        Assertions.assertEquals(testStudent, studentService.updateStudent(1L, "Стеблехвост", 21));
    }

    @Test
    public void deleteStudent() {
        studentService.deleteStudent(0L);
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> studentService.getStudentsById(0L));
        Assertions.assertEquals("Ошибка! Факультета с данным id не существует", exception.getMessage());
    }

}

