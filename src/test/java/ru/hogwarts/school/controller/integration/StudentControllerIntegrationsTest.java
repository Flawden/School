package ru.hogwarts.school.controller.integration;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:applicationTest.properties")
@Sql({"/fillDBtoTest.sql"})
public class StudentControllerIntegrationsTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getStudents() {
        Student student = new Student(0L, "Валера", 20);
        this.testRestTemplate.postForObject("http://localhost:" + port + "/api/v1/students", student, String.class);
    }

    @Test
    public void findByNameIgnoreCase() {

    }

    @Test
    public void getStudentsByAge() {

    }

    @Test
    public void getFacultyOfStudent() {

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
