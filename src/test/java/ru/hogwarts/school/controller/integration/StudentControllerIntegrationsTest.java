package ru.hogwarts.school.controller.integration;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerIntegrationsTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    public void getStudents() {
        System.out.println(this.testRestTemplate.getForObject("http://localhost:" + port + "/api/v1/students", String.class));
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
