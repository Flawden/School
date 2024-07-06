package ru.hogwarts.school.controller.integration.mockMvc;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;
import ru.hogwarts.school.service.implementation.FacultyServiceImpl;
import ru.hogwarts.school.service.implementation.StudentServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class StudentControllerIntegrationsWithMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    private static List<Student> students;
    private static List<Faculty> faculties;

    @InjectMocks
    private StudentController studentController;

    @SpyBean
    private StudentServiceImpl studentService;

    @MockBean
    private FacultyController facultyController;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private StudentRepository studentRepository;

    @BeforeEach
    public void reposInit() {
        faculties = new ArrayList<>();
        faculties.add(new Faculty(0L, "Гриффиндор", "Красный"));
        faculties.add(new Faculty(1L, "Слизерин", "Зеленый"));
        faculties.add(new Faculty(2L, "Пуффендуй", "Желтый"));
        faculties.add(new Faculty(3L, "Когтевран", "Синий"));
        faculties.add(new Faculty(4L, "Волжский политехнический техникум", "Серый"));
        students = new ArrayList<>();
        students.add(new Student(0L, "Валера", 20));
        students.add(new Student(1L, "Сережа", 15, faculties.get(1)));
        students.add(new Student(2L, "Вова", 14, faculties.get(2)));
        students.add(new Student(3L, "Стас", 20, faculties.get(3)));
        students.add(new Student(4L, "Боб", 43, faculties.get(4)));
        students.add(new Student(5L, "Авраам", 56,faculties.get(2)));
    }

    @Test
    public void getStudents() throws Exception {
        when(studentRepository.findAll())
                .thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/students")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("0"))
                .andExpect(jsonPath("$[0].name").value("Валера"))
                .andExpect(jsonPath("$[0].age").value("20"))
                .andExpect(jsonPath("$[1].id").value("1"))
                .andExpect(jsonPath("$[1].name").value("Сережа"))
                .andExpect(jsonPath("$[1].age").value("15"));
    }

    @Test
    public void findByNameIgnoreCase() throws Exception {
        String testString = students.getFirst().getName();
        List<Student> testStudentsList = new ArrayList<>();
        for (Student student: students) {
            if (student.getAge() == 20) {
                testStudentsList.add(student);
            }
        }
        when(studentRepository.findByNameIgnoreCase(testString.toString())).thenReturn(testStudentsList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/students/name/" + testString)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("0"))
                .andExpect(jsonPath("$[0].name").value("Валера"))
                .andExpect(jsonPath("$[0].age").value(20));
    }

    @Test
    public void getStudentsByAge() throws Exception {
        Integer testAge = students.getFirst().getAge();
        List<Student> testStudentsList = new ArrayList<>();
        for (Student student: students) {
            if (student.getAge() == 20) {
                testStudentsList.add(student);
            }
        }
        when(studentRepository.findStudentsByAge(testAge)).thenReturn(testStudentsList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/students/age/" + testAge)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("0"))
                .andExpect(jsonPath("$[0].name").value("Валера"))
                .andExpect(jsonPath("$[0].age").value("20"));
    }

    @Test
    public void getStudentsById() throws Exception {
        Long testId = students.getFirst().getId();
        when(studentRepository.findById(testId)).thenReturn(Optional.ofNullable(students.getFirst()));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/students/" + testId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("0"))
                .andExpect(jsonPath("$.name").value("Валера"))
                .andExpect(jsonPath("$.age").value("20"));
    }

    @Test
    public void addStudent() throws Exception {
        Student testStudent = new Student(6L, "Грегорий", 20);

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", testStudent.getName());
        studentObject.put("age", testStudent.getAge());

        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/students")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("6"))
                .andExpect(jsonPath("$.name").value("Грегорий"))
                .andExpect(jsonPath("$.age").value("20"));
    }

    @Test
    void updateStudents() throws Exception {
        Student testStudent = new Student(1L, "Грегорий", 20);

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", testStudent.getName());
        studentObject.put("age", testStudent.getAge());

        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/students")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Грегорий"))
                .andExpect(jsonPath("$.age").value("20"));
    }

    @Test
    void deleteStudent() throws Exception {
        when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(students.getFirst()));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/students/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
