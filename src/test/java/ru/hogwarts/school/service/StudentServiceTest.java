package ru.hogwarts.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    private static List<Student> students;

    @BeforeEach
    public void setUp() {
        studentService = new StudentService(studentRepository);
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
        when(studentRepository.findAll()).thenReturn(students);
        Assertions.assertEquals(students, studentService.getStudents());
    }

    @Test
    public void getStudentsByName() {
        List<Student> testStudents = students.stream().filter(s -> Objects.equals(s.getName(), students.getFirst().getName())).toList();
        when(studentRepository.findStudentsByName(students.getFirst().getName())).thenReturn(testStudents);
        Assertions.assertEquals(testStudents, studentService.getStudentsByName(students.getFirst().getName()));
    }

    @Test
    public void getStudentsByNameWithException() {
        when(studentRepository.findStudentsByName("Абвывав")).thenReturn(new ArrayList<>());
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> studentService.getStudentsByName("Абвывав"));
        Assertions.assertEquals("Ошибка! Студентов с данным именем не найдено.", exception.getMessage());
    }

    @Test
    public void getStudentsByAge() {
        List<Student> testStudents = students.stream().filter(s -> s.getAge().equals(20)).toList();
        when(studentRepository.findStudentsByAge(20)).thenReturn(testStudents);
        Assertions.assertEquals(testStudents, studentService.getStudentsByAge(20));
    }

    @Test
    public void getStudentsByAgeWithException() {
        when(studentRepository.findStudentsByAge(999)).thenReturn(new ArrayList<>());
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> studentService.getStudentsByAge(999));
        Assertions.assertEquals("Ошибка! Студентов с данным возрастом не найдено.", exception.getMessage());
    }

    @Test
    public void getStudentsById() {
        when(studentRepository.findStudentById(0L)).thenReturn(Optional.ofNullable(students.getFirst()));
        Assertions.assertEquals(students.getFirst(), studentService.getStudentsById(0L));
    }

    @Test
    public void getStudentsByIdWithException() {
        when(studentRepository.findStudentById(students.size() + 1L)).thenReturn(Optional.empty());
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> studentService.getStudentsById(students.size() + 1L));
        Assertions.assertEquals("Ошибка! Факультета с данным id не найдено", exception.getMessage());
    }

    @Test
    public void addStudent() {
        Student testStudent = new Student("Глеб", 12);
        when(studentRepository.save(testStudent)).thenReturn(testStudent);
        Assertions.assertEquals(testStudent, studentService.addStudent("Глеб", 12));
    }

    @Test
    public void updateStudent() {
        Student testStudent = new Student(1L, "Стеблехвост", 21);
        when(studentRepository.findStudentById(1L)).thenReturn(Optional.ofNullable(students.get(1)));
        when(studentRepository.save(testStudent)).thenReturn(testStudent);
        Assertions.assertEquals(testStudent, studentService.updateStudent(1L, "Стеблехвост", 21));
    }

    @Test
    public void deleteStudentWithException() {
        when(studentRepository.findStudentById(0L)).thenReturn(Optional.empty());
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> studentService.deleteStudent(0L));
        Assertions.assertEquals("Ошибка! Студента с данным id не существует", exception.getMessage());
    }

}

