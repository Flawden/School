package ru.hogwarts.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.HashMap;

public class FacultyServiceTest {

    private FacultyService facultyService;

    @Mock
    private FacultyRepository facultyRepository;

    private static HashMap<Long, Faculty> faculties;

    @BeforeEach
    public void setUp() {
        facultyService = new FacultyService(facultyRepository);
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
        Assertions.assertEquals(faculties, facultyService.getFaculties());
    }

    @Test
    public void getFacultiesByName() {
        Assertions.assertEquals(faculties.get(0L), facultyService.getFacultiesByName(faculties.get(0L).getName()));
    }

    @Test
    public void getFacultiesByNameWithException() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> facultyService.getFacultiesByName("Фламма"));
        Assertions.assertEquals("Ошибка! Факультета с данным названием не существует", exception.getMessage());
    }

    @Test
    public void getFacultiesByColor() {
        Assertions.assertEquals(faculties.get(0L), facultyService.getFacultiesByColor(faculties.get(0L).getColor()));
    }

    @Test
    public void getFacultiesByColorWithException() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> facultyService.getFacultiesByColor("Радужный"));
        Assertions.assertEquals("Ошибка! Факультета с данным цветом не существует", exception.getMessage());
    }

    @Test
    public void getFacultiesById() {
        Assertions.assertEquals(faculties.get(0L), facultyService.getFacultiesById(0L));
    }

    @Test
    public void getFacultiesByIdWithException() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> facultyService.getFacultiesById(faculties.size() + 1L));
        Assertions.assertEquals("Ошибка! Факультета с данным id не существует", exception.getMessage());
    }

    @Test
    public void addFaculty() {
        Faculty testFaculty = new Faculty(faculties.size() + 1L, "Стеблехвост", "Черный");
        Assertions.assertEquals(testFaculty, facultyService.addFaculty("Стеблехвост", "Черный"));
    }

    @Test
    public void addFacultyWithExceptions() {
        IllegalArgumentException exceptionByName = Assertions.assertThrows(IllegalArgumentException.class, () -> facultyService.addFaculty(faculties.get(0L).getName(), "Фиолетовый"));
        IllegalArgumentException exceptionByColor = Assertions.assertThrows(IllegalArgumentException.class, () -> facultyService.addFaculty("Стеблкрон", faculties.get(0L).getColor()));
        Assertions.assertEquals("Ошибка! Факультет с данным названием уже существует!", exceptionByName.getMessage());
        Assertions.assertEquals("Ошибка! Факультет с таким цветом уже существует!", exceptionByColor.getMessage());
    }

    @Test
    public void updateFaculty() {
        Faculty testFaculty = new Faculty(1L, "Стеблехвост", "Черный");
        Assertions.assertEquals(testFaculty, facultyService.updateFaculty(1L, "Стеблехвост", "Черный"));
    }

    @Test
    public void deleteFaculty() {
        facultyService.deleteFaculty(0L);
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> facultyService.getFacultiesById(0L));
        Assertions.assertEquals("Ошибка! Факультета с данным id не существует", exception.getMessage());
    }

}
