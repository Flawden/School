package ru.hogwarts.school.controller.integration.testRestTemplate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerIntegrationsTest {

    @LocalServerPort
    private int port;

    private String appLink = "http://localhost:";

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static List<Faculty> faculties;

    @BeforeEach
    public void reposInit() {
        faculties = new ArrayList<>();
        faculties.add(new Faculty("Гриффиндор", "Красный"));
        faculties.add(new Faculty("Слизерин", "Зеленый"));
        faculties.add(new Faculty("Пуффендуй", "Желтый"));
        faculties.add(new Faculty("Когтевран", "Синий"));
        faculties.add(new Faculty("Волжский политехнический техникум", "Серый"));
    }

    @Test
    public void getFaculties() {
        Faculty testFaculty = faculties.getFirst();
        Faculty testFaculty2 = faculties.get(1);
        ResponseEntity<Faculty> testFacultyResponse = testRestTemplate.postForEntity(
                appLink + port + "/api/v1/faculties",
                testFaculty,
                Faculty.class
        );
        ResponseEntity<Faculty> testFacultyResponse2 = testRestTemplate.postForEntity(
                appLink + port + "/api/v1/faculties",
                testFaculty2,
                Faculty.class
        );
        testFaculty = testFacultyResponse.getBody();
        testFaculty2 = testFacultyResponse2.getBody();
        ArrayList<Faculty> facultiesTest = new ArrayList<>();
        facultiesTest.add(testFaculty);
        facultiesTest.add(testFaculty2);
        ResponseEntity<ArrayList> findResponse = testRestTemplate.getForEntity(appLink + port + "/api/v1/faculties", ArrayList.class);
        Assertions.assertEquals(2, findResponse.getBody().size());
        testRestTemplate.delete(appLink + port + "/api/v1/faculties/" + testFaculty.getId());
        testRestTemplate.delete(appLink + port + "/api/v1/faculties/" + testFaculty2.getId());
    }

    @Test
    public void getByNameIgnoreCase() {
        ResponseEntity<Faculty> facultyResponseEntity = testRestTemplate.postForEntity(
                appLink + port + "/api/v1/faculties",
                faculties.getFirst(),
                Faculty.class
        );
        String savedFacultyName = Objects.requireNonNull(facultyResponseEntity.getBody()).getName();
        ResponseEntity<Faculty> findResponse = testRestTemplate.getForEntity(appLink + port + "/api/v1/faculties/name/" + faculties.getFirst().getName(), Faculty.class);
        Assertions.assertEquals(Objects.requireNonNull(findResponse.getBody()).getName(), savedFacultyName);
        testRestTemplate.delete(appLink + port + "/api/v1/faculties/" + facultyResponseEntity.getBody().getId());
    }

    @Test
    public void getByColorIgnoreCase() {
        ResponseEntity<Faculty> facultyResponseEntity = testRestTemplate.postForEntity(
                appLink + port + "/api/v1/faculties",
                faculties.getFirst(),
                Faculty.class
        );
        String savedFacultyColor = Objects.requireNonNull(facultyResponseEntity.getBody()).getColor();
        ResponseEntity<Faculty> findResponse = testRestTemplate.getForEntity(appLink + port + "/api/v1/faculties/name/" + faculties.getFirst().getName(), Faculty.class);
        Assertions.assertEquals(Objects.requireNonNull(findResponse.getBody()).getColor(), savedFacultyColor);
        testRestTemplate.delete(appLink + port + "/api/v1/faculties/" + facultyResponseEntity.getBody().getId());
    }

    @Test
    public void addFaculty() {
        ResponseEntity<Faculty> facultyResponseEntity = testRestTemplate.postForEntity(
                appLink + port + "/api/v1/faculties",
                faculties.getFirst(),
                Faculty.class
        );
        String savedFacultyName = Objects.requireNonNull(facultyResponseEntity.getBody()).getName();
        ResponseEntity<Faculty> findResponse = testRestTemplate.getForEntity(appLink + port + "/api/v1/faculties/name/" + faculties.getFirst().getName(), Faculty.class);
        Assertions.assertEquals(Objects.requireNonNull(findResponse.getBody()).getName(), savedFacultyName);

        testRestTemplate.delete(appLink + port + "/api/v1/faculties/" + facultyResponseEntity.getBody().getId());
    }

    @Test
    public void deleteFaculty() {
        ResponseEntity<Faculty> facultyResponseEntity = testRestTemplate.postForEntity(
                appLink + port + "/api/v1/faculties",
                faculties.getFirst(),
                Faculty.class
        );
        String savedFacultyName = Objects.requireNonNull(facultyResponseEntity.getBody()).getName();
        ResponseEntity<Faculty> findResponse = testRestTemplate.getForEntity(appLink + port + "/api/v1/faculties/name/" + faculties.getFirst().getName(), Faculty.class);
        Assertions.assertEquals(Objects.requireNonNull(findResponse.getBody()).getName(), savedFacultyName);

        testRestTemplate.delete(appLink + port + "/api/v1/faculties/" + facultyResponseEntity.getBody().getId());
        ResponseEntity<Faculty> oneMoreFindResponse = null;
        try {
            oneMoreFindResponse = testRestTemplate.getForEntity(appLink + port + "/api/v1/faculties/name/" + savedFacultyName, Faculty.class);
        } catch (Exception e) {
        }

        Assertions.assertNull(oneMoreFindResponse);
    }

}
