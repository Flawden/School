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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.model.Faculty;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerIntegrationsTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    public void getFaculties() {

    }

    @Test
    public void getByNameIgnoreCase() {

    }

    @Test
    public void getByColorIgnoreCase() {

    }

    @Test
    public void getStudentsOfFaculty() {

    }

    @Test
    public void getFacultiesById() {

    }

    @Test
    public void addFaculty() {

    }

    @Test
    public void updateFaculty() {

    }

    @Test
    public void deleteFaculty() {

    }

}
