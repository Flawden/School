package ru.hogwarts.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.implementation.StudentServiceImpl;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

    private StudentServiceImpl studentServiceImpl;

    @Mock
    private StudentRepository studentRepository;

    private static List<Student> students;

    @BeforeEach
    public void setUp() {
        studentServiceImpl = new StudentServiceImpl(studentRepository);
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
        Assertions.assertEquals(students, studentServiceImpl.getStudents());
    }

    @Test
    public void getStudentsByName() {
        List<Student> testStudents = students.stream().filter(s -> Objects.equals(s.getName(), students.getFirst().getName())).toList();
        when(studentRepository.findStudentsByName(students.getFirst().getName())).thenReturn(testStudents);
        Assertions.assertEquals(testStudents, studentServiceImpl.getStudentsByName(students.getFirst().getName()));
    }

    @Test
    public void getStudentsByNameWithException() {
        when(studentRepository.findStudentsByName("Абвывав")).thenReturn(new ArrayList<>());
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> studentServiceImpl.getStudentsByName("Абвывав"));
        Assertions.assertEquals("Ошибка! Студентов с данным именем не найдено.", exception.getMessage());
    }

    @Test
    public void getStudentsByAge() {
        List<Student> testStudents = students.stream().filter(s -> s.getAge().equals(20)).toList();
        when(studentRepository.findStudentsByAge(20)).thenReturn(testStudents);
        Assertions.assertEquals(testStudents, studentServiceImpl.getStudentsByAge(20));
    }

    @Test
    public void getStudentsByAgeWithException() {
        when(studentRepository.findStudentsByAge(999)).thenReturn(new ArrayList<>());
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> studentServiceImpl.getStudentsByAge(999));
        Assertions.assertEquals("Ошибка! Студентов с данным возрастом не найдено.", exception.getMessage());
    }

    @Test
    public void getStudentsById() {
        when(studentRepository.findById(0L)).thenReturn(Optional.ofNullable(students.getFirst()));
        Assertions.assertEquals(students.getFirst(), studentServiceImpl.getStudentsById(0L));
    }

    @Test
    public void getStudentsByIdWithException() {
        when(studentRepository.findById(students.size() + 1L)).thenReturn(Optional.empty());
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> studentServiceImpl.getStudentsById(students.size() + 1L));
        Assertions.assertEquals("Ошибка! Студента с данным id не найдено", exception.getMessage());
    }

    @Test
    public void addStudent() {
        Student testStudent = new Student("Глеб", 12);
        when(studentRepository.save(testStudent)).thenReturn(testStudent);
        Assertions.assertEquals(testStudent, studentServiceImpl.addStudent("Глеб", 12));
    }

    @Test
    public void updateStudent() {
        Student testStudent = new Student(1L, "Стеблехвост", 21);
        when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(students.get(1)));
        when(studentRepository.save(testStudent)).thenReturn(testStudent);
        Assertions.assertEquals(testStudent, studentServiceImpl.updateStudent(1L, new Student("Стеблехвост", 21)));
    }

    @Test
    public void deleteStudentWithException() {
        when(studentRepository.findById(0L)).thenReturn(Optional.empty());
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> studentServiceImpl.deleteStudent(0L));
        Assertions.assertEquals("Ошибка! Студента с данным id не найдено", exception.getMessage());
    }

}

