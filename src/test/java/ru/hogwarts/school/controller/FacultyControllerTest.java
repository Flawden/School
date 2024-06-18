package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FacultyControllerTest {

    @Mock
    private FacultyService facultyService;

    private FacultyController facultyController;

    private static List<Faculty> faculties;

    @BeforeEach
    public void setUp() {
        facultyController = new FacultyController(facultyService);
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

        when(facultyService.getFaculties()).thenReturn(faculties);
        Assertions.assertEquals(faculties, facultyController.getFaculties());
    }

    @Test
    public void getFacultiesByName() {
        when(facultyService.getFacultyByName("Гриффиндор")).thenReturn(faculties.getFirst());
        Assertions.assertEquals(faculties.getFirst(), facultyController.getFacultiesByName("Гриффиндор"));
    }

    @Test
    public void getFacultiesByColor() {
        when(facultyService.getFacultiesByColor("Красный")).thenReturn(faculties.getFirst());
        Assertions.assertEquals(faculties.getFirst(), facultyController.getFacultiesByColor("Красный"));
    }

    @Test
    public void getFacultiesById() {
        when(facultyService.getFacultiesById(0L)).thenReturn(faculties.getFirst());
        Assertions.assertEquals(faculties.getFirst(), facultyController.getFacultiesById(0L));
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
