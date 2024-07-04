package ru.hogwarts.school.controller.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import ru.hogwarts.school.model.Faculty;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerIntegrationsTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static List<Faculty> faculties;

    @BeforeEach
    public void reposInit() {
        faculties = new ArrayList<>();
        faculties.add(new Faculty(0L, "Гриффиндор", "Красный"));
        faculties.add(new Faculty(1L, "Слизерин", "Зеленый"));
        faculties.add(new Faculty(2L, "Пуффендуй", "Желтый"));
        faculties.add(new Faculty(3L, "Когтевран", "Синий"));
        faculties.add(new Faculty(4L, "Волжский политехнический техникум", "Серый"));
    }

    public void getFaculties() {
        //Assertions.assertEquals(faculties, testRestTemplate.execute("http://localhost:" + port + "api/v1/students", HttpMethod.GET, );
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
