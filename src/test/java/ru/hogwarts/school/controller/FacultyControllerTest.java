package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.HashMap;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FacultyControllerTest {

    @Mock
    private FacultyService facultyService;

    private FacultyController facultyController;

    private static HashMap<Long, Faculty> faculties;

    @BeforeEach
    public void setUp() {
        facultyController = new FacultyController(facultyService);
    }

    @BeforeEach
    public void reposInit() {
        faculties = new HashMap<>();
        faculties.put(0L, new Faculty(0L, "Гриффиндор", "Красный"));
        faculties.put(1L, new Faculty(1L, "Слизерин", "Зеленый"));
        faculties.put(2L, new Faculty(2L, "Пуффендуй", "Желтый"));
        faculties.put(3L, new Faculty(3L, "Когтевран", "Синий"));
        faculties.put(4L, new Faculty(4L, "Волжский политехнический техникум", "Серый"));
    }

    @Test
    public void getFaculties() {
        when(facultyService.getFaculties()).thenReturn(faculties);
        Assertions.assertEquals(faculties, facultyController.getFaculties());
    }

    @Test
    public void getFacultiesByName() {
        when(facultyService.getFacultiesByName("Гриффиндор")).thenReturn(faculties.get(0L));
        Assertions.assertEquals(faculties.get(0L), facultyController.getFacultiesByName("Гриффиндор"));
    }

    @Test
    public void getFacultiesByColor() {
        when(facultyService.getFacultiesByColor("Красный")).thenReturn(faculties.get(0L));
        Assertions.assertEquals(faculties.get(0L), facultyController.getFacultiesByColor("Красный"));
    }

    @Test
    public void getFacultiesById() {
        when(facultyService.getFacultiesById(0L)).thenReturn(faculties.get(0L));
        Assertions.assertEquals(faculties.get(0L), facultyController.getFacultiesById(0L));
    }

    @Test
    public void addFaculty() {
        Faculty testFaculty = new Faculty(faculties.size() + 1L, "Светлодуй", "Голубой");
        when(facultyService.addFaculty("Светлодуй", "Голубой")).thenReturn(testFaculty);
        Assertions.assertEquals(testFaculty, facultyController.addFaculty("Светлодуй", "Голубой"));
    }

    @Test
    public void updateFaculty() {
        Faculty testFaculty = new Faculty(0L, "Светлодуй", "Голубой");
        when(facultyService.updateFaculty(0L, "Светлодуй", "Голубой")).thenReturn(testFaculty);
        Assertions.assertEquals(testFaculty, facultyController.updateFaculty(0L, "Светлодуй", "Голубой"));
    }

}
