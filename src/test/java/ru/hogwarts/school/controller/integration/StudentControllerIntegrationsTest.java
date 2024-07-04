package ru.hogwarts.school.controller.integration;

import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:applicationTest.properties")
public class StudentControllerIntegrationsTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private final ModelMapper mapper = new ModelMapper();

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
        students.add(new Student(0L, "Валера", 20, faculties.getFirst(), 112L));
        students.add(new Student(1L, "Сережа", 15, faculties.get(1), 113L));
        students.add(new Student(2L, "Вова", 14, faculties.get(2), 114L));
        students.add(new Student(3L, "Стас", 20, faculties.get(3), 115L));
        students.add(new Student(4L, "Боб", 43, faculties.get(4), 116L));
        students.add(new Student(5L, "Авраам", 56,faculties.get(2), 117L));
    }

    @Test
    public void getStudents() {
        String expected = "[{\"name\":\"Валера\",\"age\":20,\"studentIdNumber\":112},{\"name\":\"Сережа\",\"age\":15,\"studentIdNumber\":113},{\"name\":\"Вова\",\"age\":14,\"studentIdNumber\":114},{\"name\":\"Стас\",\"age\":20,\"studentIdNumber\":115},{\"name\":\"Боб\",\"age\":43,\"studentIdNumber\":116},{\"name\":\"Авраам\",\"age\":56,\"studentIdNumber\":117}]";
        Assertions.assertEquals(expected, this.testRestTemplate.getForObject("http://localhost:" + port + "/api/v1/students", String.class));
    }

    @Test
    public void findByNameIgnoreCase() {
        String expected = "[{\"name\":\"Валера\",\"age\":20,\"studentIdNumber\":112}]";
        Assertions.assertEquals(expected, this.testRestTemplate.getForObject("http://localhost:" + port + "/api/v1/students/name/Валера", String.class));
    }

    @Test
    public void getStudentsByAge() {
        String expected = "[{\"name\":\"Валера\",\"age\":20,\"studentIdNumber\":112},{\"name\":\"Стас\",\"age\":20,\"studentIdNumber\":115}]";
        Assertions.assertEquals(expected, this.testRestTemplate.getForObject("http://localhost:" + port + "/api/v1/students/age/20", String.class));
    }

    @Test
    public void findByAgeBetween() {
        String expected = "[{\"name\":\"Валера\",\"age\":20,\"studentIdNumber\":112},{\"name\":\"Стас\",\"age\":20,\"studentIdNumber\":115}]";
        Assertions.assertEquals(expected, this.testRestTemplate.getForObject("http://localhost:" + port + "/api/v1/students/age/between/20/22", String.class));
    }

    @Test
    public void getStudentsById() {
        String expected = "{\"name\":\"Сережа\",\"age\":15,\"studentIdNumber\":113}";
        Assertions.assertEquals(expected, this.testRestTemplate.getForObject("http://localhost:" + port + "/api/v1/students/1", String.class));
    }

    @Test
    public void addStudent() {
        StudentDTO studentDTO = mapper.map(new Student(null, "Молдован", 42, faculties.get(1), 119L), StudentDTO.class);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StudentDTO> studentDTOHttpEntity = new HttpEntity<>(studentDTO, httpHeaders);
        String expected = "<200 OK OK,{\"name\":\"Молдован\",\"age\":42,\"studentIdNumber\":119},[Content-Type:\"application/json\"";
        String string = String.valueOf(testRestTemplate.exchange("http://localhost:" + port + "/api/v1/students", HttpMethod.POST, studentDTOHttpEntity, String.class));

        Assertions.assertTrue(string.contains(expected));
    }

    @Test
    void updateStudents() {
        String expected = "";
        Assertions.assertEquals(expected, this.testRestTemplate.getForObject("http://localhost:" + port + "/api/v1/students/name/Валера", String.class));
    }

    @Test
    void deleteStudent() {
        String string = String.valueOf(this.testRestTemplate.exchange("http://localhost:" + port + "/api/v1/students/13", HttpMethod.DELETE, HttpEntity.EMPTY, String.class));
        Assertions.assertTrue(string.contains("<200 OK OK,[Content-Length:\"0\""));
    }

}
