package ru.hogwarts.school.controller.integration.mockMvc;

import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.implementation.FacultyServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class FacultyControllerIntegrationsWithMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private FacultyController facultyController;

    @MockBean
    private StudentController studentController;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private StudentRepository studentRepository;

    private String appLink = "http://localhost:";

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
    public void getFaculties() throws Exception {
        when(facultyRepository.findAll())
                .thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/faculties")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getByNameIgnoreCase() {

    }

    @Test
    public void getByColorIgnoreCase() {

    }

    @Test
    public void addFaculty() throws Exception {
        final Long id = 1L;
        final String name = "Блабладор";
        final String color = "Коричневый";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty(id, name, color);

        when(facultyRepository.save(any(Faculty.class)))
                .thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/faculties")
                .content(facultyObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/faculties/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void deleteFaculty() {

    }

}
