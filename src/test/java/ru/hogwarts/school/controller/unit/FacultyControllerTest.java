package ru.hogwarts.school.controller.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.implementation.FacultyServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FacultyControllerTest {

    @Mock
    private FacultyServiceImpl facultyServiceImpl;

    private FacultyController facultyController;

    private static List<Faculty> faculties;

    private static ModelMapper mapper = new ModelMapper();

    @BeforeEach
    public void setUp() {
        facultyController = new FacultyController(facultyServiceImpl, mapper);
    }

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

        when(facultyServiceImpl.getFaculties()).thenReturn(faculties);
        Assertions.assertEquals(faculties, facultyController.getFaculties());
    }

    @Test
    public void getFacultiesByName() {
        when(facultyServiceImpl.getByNameIgnoreCase("Гриффиндор")).thenReturn(faculties.getFirst());
        Assertions.assertEquals(faculties.getFirst(), facultyController.getByNameIgnoreCase("Гриффиндор"));
    }

    @Test
    public void getFacultiesByColor() {
        when(facultyServiceImpl.getByColorIgnoreCase("Красный")).thenReturn(faculties.getFirst());
        Assertions.assertEquals(faculties.getFirst(), facultyController.getByColorIgnoreCase("Красный"));
    }

    @Test
    public void getFacultiesById() {
        when(facultyServiceImpl.getFacultiesById(0L)).thenReturn(faculties.getFirst());
        Assertions.assertEquals(faculties.getFirst(), facultyController.getFacultiesById(0L));
    }

    @Test
    public void addFaculty() {
        Faculty testFaculty = new Faculty(faculties.size() + 1L, "Светлодуй", "Голубой");
        when(facultyServiceImpl.addFaculty(new Faculty("Светлодуй", "Голубой"))).thenReturn(testFaculty);
        Assertions.assertEquals(testFaculty, facultyController.addFaculty(new FacultyDTO("Светлодуй", "Голубой")));
    }

    @Test
    public void updateFaculty() {
        Faculty testFaculty = new Faculty(0L, "Светлодуй", "Голубой");
        when(facultyServiceImpl.updateFaculty(faculties.getFirst().getName(), new Faculty("Светлодуй", "Голубой"))).thenReturn(testFaculty);
        Assertions.assertEquals(testFaculty, facultyController.updateFaculty(faculties.getFirst().getName(), new FacultyDTO("Светлодуй", "Голубой")));
    }

}
