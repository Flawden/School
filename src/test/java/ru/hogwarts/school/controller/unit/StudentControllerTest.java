package ru.hogwarts.school.controller.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.dto.StudentWithFacultyDTO;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.implementation.StudentServiceImpl;
import ru.hogwarts.school.util.StudentMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @Mock
    private StudentServiceImpl studentServiceImpl;

    private static List<Student> students;

    private StudentController studentController;

    private static ModelMapper mapper = new ModelMapper();
    private static StudentMapper studentMapper = new StudentMapper(mapper);

    @BeforeEach
    public void setUp() {
        studentController = new StudentController(studentServiceImpl, mapper, studentMapper);
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
    public void getStudentsById() {
        when(studentServiceImpl.getStudentsById(0L)).thenReturn(students.getFirst());
        Assertions.assertEquals(students.getFirst(), studentController.getStudentsById(0L));
    }

    @Test
    public void addStudents() {
        Student testStudent = new Student(students.size() + 1L, "Сережа", 25);
        when(studentServiceImpl.addStudent(new Student("Сережа", 25))).thenReturn(testStudent);
        Assertions.assertEquals(testStudent, studentController.addStudent(new StudentDTO("Сережа", 25, 123L)));
    }

    @Test
    public void updateStudents() {
        Student testStudent = new Student(0L, "Сережа", 25);
        when(studentServiceImpl.updateStudent(0L, new Student("Сережа", 25))).thenReturn(testStudent);
        Assertions.assertEquals(testStudent, studentController.updateStudents(0L, new StudentWithFacultyDTO("Сережа", 25, 123L, new FacultyDTO())));
    }

}
