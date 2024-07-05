package ru.hogwarts.school.controller.integration;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.dto.FacultyDTO;
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
    @Autowired
    private ModelMapper mapper;

    @BeforeEach
    public void reposInit() {
        faculties = new ArrayList<>();
        faculties.add(new Faculty(0L, "Гриффиндор", "Красный"));
        faculties.add(new Faculty(1L, "Слизерин", "Зеленый"));
        faculties.add(new Faculty(2L, "Пуффендуй", "Желтый"));
        faculties.add(new Faculty(3L, "Когтевран", "Синий"));
        faculties.add(new Faculty(4L, "Волжский политехнический техникум", "Серый"));
    }

    @Test
    public void getFaculties() {
        FacultyDTO testFacultyDTO = mapper.map(faculties.getFirst(), FacultyDTO.class);
        FacultyDTO testFacultyDTO2 = mapper.map(faculties.get(1), FacultyDTO.class);
        testRestTemplate.postForEntity(
                appLink + port + "/api/v1/faculties",
                testFacultyDTO,
                FacultyDTO.class
        );
        testRestTemplate.postForEntity(
                appLink + port + "/api/v1/faculties",
                testFacultyDTO2,
                FacultyDTO.class
        );
        ArrayList<FacultyDTO> facultyDTOS = new ArrayList<>();
        facultyDTOS.add(testFacultyDTO);
        facultyDTOS.add(testFacultyDTO2);
        ResponseEntity<ArrayList> findResponse = testRestTemplate.getForEntity(appLink + port + "/api/v1/faculties", ArrayList.class);
        Assertions.assertEquals(facultyDTOS.toString(), findResponse.getBody().toString());
        testRestTemplate.delete(appLink + port + "/api/v1/faculties/" + testFacultyDTO.getName());
        testRestTemplate.delete(appLink + port + "/api/v1/faculties/" + testFacultyDTO2.getName());
    }

    @Test
    public void getByNameIgnoreCase() {
        ResponseEntity<FacultyDTO> facultyResponseEntity = testRestTemplate.postForEntity(
                appLink + port + "/api/v1/faculties",
                mapper.map(faculties.getFirst(), FacultyDTO.class),
                FacultyDTO.class
        );
        String savedFacultyName = Objects.requireNonNull(facultyResponseEntity.getBody()).getName();
        ResponseEntity<FacultyDTO> findResponse = testRestTemplate.getForEntity(appLink + port + "/api/v1/faculties/name/" + faculties.getFirst().getName(), FacultyDTO.class);
        Assertions.assertEquals(Objects.requireNonNull(findResponse.getBody()).getName(), savedFacultyName);
        testRestTemplate.delete(appLink + port + "/api/v1/faculties/" + savedFacultyName);
    }

    @Test
    public void getByColorIgnoreCase() {
        ResponseEntity<FacultyDTO> facultyResponseEntity = testRestTemplate.postForEntity(
                appLink + port + "/api/v1/faculties",
                mapper.map(faculties.getFirst(), FacultyDTO.class),
                FacultyDTO.class
        );
        String savedFacultyColor = Objects.requireNonNull(facultyResponseEntity.getBody()).getColor();
        ResponseEntity<FacultyDTO> findResponse = testRestTemplate.getForEntity(appLink + port + "/api/v1/faculties/name/" + faculties.getFirst().getName(), FacultyDTO.class);
        Assertions.assertEquals(Objects.requireNonNull(findResponse.getBody()).getColor(), savedFacultyColor);
        testRestTemplate.delete(appLink + port + "/api/v1/faculties/" + savedFacultyColor);
    }

    @Test
    public void addFaculty() {
        ResponseEntity<FacultyDTO> facultyResponseEntity = testRestTemplate.postForEntity(
                appLink + port + "/api/v1/faculties",
                mapper.map(faculties.getFirst(), FacultyDTO.class),
                FacultyDTO.class
        );
        String savedFacultyName = Objects.requireNonNull(facultyResponseEntity.getBody()).getName();
        ResponseEntity<FacultyDTO> findResponse = testRestTemplate.getForEntity(appLink + port + "/api/v1/faculties/name/" + faculties.getFirst().getName(), FacultyDTO.class);
        Assertions.assertEquals(Objects.requireNonNull(findResponse.getBody()).getName(), savedFacultyName);

        testRestTemplate.delete(appLink + port + "/api/v1/faculties/" + savedFacultyName);
    }

    @Test
    public void deleteFaculty() {
        ResponseEntity<FacultyDTO> facultyResponseEntity = testRestTemplate.postForEntity(
                appLink + port + "/api/v1/faculties",
                mapper.map(faculties.getFirst(), FacultyDTO.class),
                FacultyDTO.class
        );
        String savedFacultyName = Objects.requireNonNull(facultyResponseEntity.getBody()).getName();
        ResponseEntity<FacultyDTO> findResponse = testRestTemplate.getForEntity(appLink + port + "/api/v1/faculties/name/" + faculties.getFirst().getName(), FacultyDTO.class);
        Assertions.assertEquals(Objects.requireNonNull(findResponse.getBody()).getName(), savedFacultyName);

        testRestTemplate.delete(appLink + port + "/api/v1/faculties/" + savedFacultyName);
        ResponseEntity<FacultyDTO> oneMoreFindResponse = null;
        try {
            oneMoreFindResponse = testRestTemplate.getForEntity(appLink + port + "/api/v1/faculties/name/" + savedFacultyName, FacultyDTO.class);
        } catch (Exception e) {}

        Assertions.assertNull(oneMoreFindResponse);
    }

}
