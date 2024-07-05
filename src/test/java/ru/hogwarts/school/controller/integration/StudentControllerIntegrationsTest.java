package ru.hogwarts.school.controller.integration;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:applicationTest.properties")
public class StudentControllerIntegrationsTest {

    @LocalServerPort
    private int port;

    private String appLink = "http://localhost:";

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static List<Student> students;
    private static List<Faculty> faculties;



    @BeforeEach
    public void reposInit() {
        faculties = new ArrayList<>();
        faculties.add(new Faculty(0L, "Гриффиндор", "Красный"));
        faculties.add(new Faculty(1L, "Слизерин", "Зеленый"));
        faculties.add(new Faculty(2L, "Пуффендуй", "Желтый"));
        faculties.add(new Faculty(3L, "Когтевран", "Синий"));
        faculties.add(new Faculty(4L, "Волжский политехнический техникум", "Серый"));
        students = new ArrayList<>();
        students.add(new Student(0L, "Валера", 20, faculties.getFirst()));
        students.add(new Student(1L, "Сережа", 15, faculties.get(1)));
        students.add(new Student(2L, "Вова", 14, faculties.get(2)));
        students.add(new Student(3L, "Стас", 20, faculties.get(3)));
        students.add(new Student(4L, "Боб", 43, faculties.get(4)));
        students.add(new Student(5L, "Авраам", 56,faculties.get(2)));
    }

    @Test
    public void getStudents() {

    }

    @Test
    public void findByNameIgnoreCase() {

    }

    @Test
    public void getStudentsByAge() {

    }

    @Test
    public void findByAgeBetween() {

    }

    @Test
    public void getStudentsById() {

    }

    @Test
    public void addStudent() {

    }

    @Test
    void updateStudents() {

    }

    @Test
    void deleteStudent() {

    }

}
