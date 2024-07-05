package ru.hogwarts.school.controller.integration;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        faculties.add(new Faculty("Гриффиндор", "Красный"));
        faculties.add(new Faculty("Слизерин", "Зеленый"));
        faculties.add(new Faculty("Пуффендуй", "Желтый"));
        faculties.add(new Faculty("Когтевран", "Синий"));
        faculties.add(new Faculty("Волжский политехнический техникум", "Серый"));
        students = new ArrayList<>();
        students.add(new Student("Валера", 20));
        students.add(new Student("Сережа", 15, faculties.get(1)));
        students.add(new Student("Вова", 14, faculties.get(2)));
        students.add(new Student("Стас", 20, faculties.get(3)));
        students.add(new Student("Боб", 43, faculties.get(4)));
        students.add(new Student("Авраам", 56,faculties.get(2)));
    }

    @Test
    public void getStudents() {
        ResponseEntity<Faculty> facultyResponseEntity = testRestTemplate.postForEntity(
                appLink + port + "/api/v1/faculties",
                faculties.getFirst(),
                Faculty.class
        );
        ResponseEntity<Student> studentResponseEntity = testRestTemplate.postForEntity(
                appLink + port + "/api/v1/students",
                students.getFirst(),
                Student.class
        );
        ArrayList<Student> studentsForTest = new ArrayList<>();
        studentsForTest.add(studentResponseEntity.getBody());
        ResponseEntity<ArrayList> findResponse = testRestTemplate.getForEntity(appLink + port + "/api/v1/students", ArrayList.class);
        Assertions.assertEquals(1, findResponse.getBody().size());
        testRestTemplate.delete(appLink + port + "/api/v1/students/" + studentResponseEntity.getBody().getId());
        testRestTemplate.delete(appLink + port + "/api/v1/faculties/" + facultyResponseEntity.getBody().getId());
    }

    @Test
    public void findByNameIgnoreCase() {
        ResponseEntity<Faculty> facultyResponseEntity = testRestTemplate.postForEntity(
                appLink + port + "/api/v1/faculties",
                faculties.getFirst(),
                Faculty.class
        );
        ResponseEntity<Student> studentResponseEntity = testRestTemplate.postForEntity(
                appLink + port + "/api/v1/students",
                students.getFirst(),
                Student.class
        );
        ArrayList<Student> studentsForTest = new ArrayList<>();
        studentsForTest.add(studentResponseEntity.getBody());
        ResponseEntity<ArrayList> findResponse = testRestTemplate.getForEntity(appLink + port + "/api/v1/students/name/" + studentResponseEntity.getBody().getName(), ArrayList.class);
        Assertions.assertTrue(findResponse.getBody().getFirst().toString().contains(studentResponseEntity.getBody().getName()));
        testRestTemplate.delete(appLink + port + "/api/v1/students/" + studentResponseEntity.getBody().getId());
        testRestTemplate.delete(appLink + port + "/api/v1/faculties/" + facultyResponseEntity.getBody().getId());
    }

    @Test
    public void getStudentsByAge() {
        ResponseEntity<Faculty> facultyResponseEntity = testRestTemplate.postForEntity(
                appLink + port + "/api/v1/faculties",
                faculties.getFirst(),
                Faculty.class
        );
        ResponseEntity<Student> studentResponseEntity = testRestTemplate.postForEntity(
                appLink + port + "/api/v1/students",
                students.getFirst(),
                Student.class
        );
        ArrayList<Student> studentsForTest = new ArrayList<>();
        studentsForTest.add(studentResponseEntity.getBody());
        ResponseEntity<ArrayList> findResponse = testRestTemplate.getForEntity(appLink + port + "/api/v1/students/age/" + studentResponseEntity.getBody().getAge(), ArrayList.class);
        Assertions.assertTrue(findResponse.getBody().getFirst().toString().contains(studentResponseEntity.getBody().getAge().toString()));
        testRestTemplate.delete(appLink + port + "/api/v1/students/" + studentResponseEntity.getBody().getId());
        testRestTemplate.delete(appLink + port + "/api/v1/faculties/" + facultyResponseEntity.getBody().getId());
    }

    @Test
    public void getStudentsById() {
        ResponseEntity<Faculty> facultyResponseEntity = testRestTemplate.postForEntity(
                appLink + port + "/api/v1/faculties",
                faculties.getFirst(),
                Faculty.class
        );
        ResponseEntity<Student> studentResponseEntity = testRestTemplate.postForEntity(
                appLink + port + "/api/v1/students",
                students.getFirst(),
                Student.class
        );
        Long savedStudentId = Objects.requireNonNull(facultyResponseEntity.getBody()).getId();
        ResponseEntity<Student> findResponse = testRestTemplate.getForEntity(appLink + port + "/api/v1/students/" + studentResponseEntity.getBody().getId(), Student.class);
        Assertions.assertEquals(Objects.requireNonNull(findResponse.getBody()).getId(), savedStudentId);
        testRestTemplate.delete(appLink + port + "/api/v1/students/" + studentResponseEntity.getBody().getId());
        testRestTemplate.delete(appLink + port + "/api/v1/faculties/" + facultyResponseEntity.getBody().getId());
    }

    @Test
    public void addStudent() {
        ResponseEntity<Faculty> facultyResponseEntity = testRestTemplate.postForEntity(
                appLink + port + "/api/v1/faculties",
                faculties.getFirst(),
                Faculty.class
        );
        ResponseEntity<Student> studentResponseEntity = testRestTemplate.postForEntity(
                appLink + port + "/api/v1/students",
                students.getFirst(),
                Student.class
        );
        Long savedStudentId = Objects.requireNonNull(facultyResponseEntity.getBody()).getId();
        ResponseEntity<Student> findResponse = testRestTemplate.getForEntity(appLink + port + "/api/v1/students/" + studentResponseEntity.getBody().getId(), Student.class);
        Assertions.assertEquals(Objects.requireNonNull(findResponse.getBody()).getId(), savedStudentId);
        testRestTemplate.delete(appLink + port + "/api/v1/students/" + studentResponseEntity.getBody().getId());
        testRestTemplate.delete(appLink + port + "/api/v1/faculties/" + facultyResponseEntity.getBody().getId());
    }

    @Test
    void updateStudents() {

    }

    @Test
    void deleteStudent() {
        ResponseEntity<Faculty> facultyResponseEntity = testRestTemplate.postForEntity(
                appLink + port + "/api/v1/faculties",
                faculties.getFirst(),
                Faculty.class
        );
        ResponseEntity<Student> studentResponseEntity = testRestTemplate.postForEntity(
                appLink + port + "/api/v1/students",
                students.getFirst(),
                Student.class
        );
        Long savedStudentId = Objects.requireNonNull(facultyResponseEntity.getBody()).getId();
        ResponseEntity<Student> findResponse = testRestTemplate.getForEntity(appLink + port + "/api/v1/students/" + studentResponseEntity.getBody().getId(), Student.class);
        Assertions.assertEquals(Objects.requireNonNull(findResponse.getBody()).getId(), savedStudentId);
        testRestTemplate.delete(appLink + port + "/api/v1/students/" + studentResponseEntity.getBody().getId());
        testRestTemplate.delete(appLink + port + "/api/v1/faculties/" + facultyResponseEntity.getBody().getId());
        ResponseEntity<Student> oneMoreFindResponse = null;
        try {
            oneMoreFindResponse = testRestTemplate.getForEntity(appLink + port + "/api/v1/students/" + savedStudentId, Student.class);
        } catch (Exception e) {}

        Assertions.assertNull(oneMoreFindResponse);
    }

}
