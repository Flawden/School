package ru.hogwarts.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.implementation.FacultyServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceImplTest {

    private FacultyServiceImpl facultyServiceImpl;

    @Mock
    private FacultyRepository facultyRepository;

    private static List<Faculty> faculties;

    @BeforeEach
    public void setUp() {
        facultyServiceImpl = new FacultyServiceImpl(facultyRepository);
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
        when(facultyRepository.findAll()).thenReturn(faculties);
        Assertions.assertEquals(faculties, facultyServiceImpl.getFaculties());
    }

    @Test
    public void getFacultiesByName() {
        Optional<Faculty> testFaculty = Optional.ofNullable(faculties.getFirst());
        when(facultyRepository.getFacultyByName(faculties.getFirst().getName())).thenReturn(testFaculty);
        Assertions.assertEquals(faculties.getFirst(), facultyServiceImpl.getFacultyByName(faculties.getFirst().getName()));
    }

    @Test
    public void getFacultiesByNameWithException() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> facultyServiceImpl.getFacultyByName("Фламма"));
        Assertions.assertEquals("Ошибка! Факультета с данным названием не существует", exception.getMessage());
    }

    @Test
    public void getFacultiesByColor() {
        when(facultyRepository.getFacultyByColor(faculties.getFirst().getColor())).thenReturn(Optional.ofNullable(faculties.getFirst()));
        Assertions.assertEquals(faculties.getFirst(), facultyServiceImpl.getFacultiesByColor(faculties.getFirst().getColor()));
    }

    @Test
    public void getFacultiesByColorWithException() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> facultyServiceImpl.getFacultiesByColor("Радужный"));
        Assertions.assertEquals("Ошибка! Факультета с данным цветом не существует", exception.getMessage());
    }

    @Test
    public void getFacultiesById() {
        Optional<Faculty> testFaculty = Optional.ofNullable(faculties.getFirst());
        when(facultyRepository.findById(faculties.getFirst().getId())).thenReturn(testFaculty);
        Assertions.assertEquals(faculties.getFirst(), facultyServiceImpl.getFacultiesById(0L));
    }

    @Test
    public void getFacultiesByIdWithException() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> facultyServiceImpl.getFacultiesById(faculties.size() + 1L));
        Assertions.assertEquals("Ошибка! Факультета с данным id не существует", exception.getMessage());
    }

    @Test
    public void addFaculty() {
        Faculty testFaculty = new Faculty("Стеблехвост", "Черный");
        when(facultyRepository.save(testFaculty)).thenReturn(testFaculty);
        when(facultyRepository.findByNameOrColor("Стеблехвост", "Черный")).thenReturn(new ArrayList<>());
        Assertions.assertEquals(testFaculty, facultyServiceImpl.addFaculty(new Faculty("Стеблехвост", "Черный")));
    }

    @Test
    public void addFacultyWithExceptions() {
        List<Faculty> testFaculties = faculties.stream().
                filter(s -> s.getColor().equals("Фиолетовый") || s.getName().equals(faculties.getFirst().getName())).toList();
        List<Faculty> testFaculties2 = faculties.stream().
                filter(s -> s.getColor().equals(faculties.getFirst().getColor()) || s.getName().equals("Стеблкрон")).toList();
        when(facultyRepository.findByNameOrColor(faculties.getFirst().getName(), "Фиолетовый")).thenReturn(testFaculties);
        when(facultyRepository.findByNameOrColor("Стеблкрон", faculties.getFirst().getColor())).thenReturn(testFaculties2);
        IllegalArgumentException exceptionByName = Assertions.assertThrows(IllegalArgumentException.class, () -> facultyServiceImpl.addFaculty(new Faculty(faculties.getFirst().getName(), "Фиолетовый")));
        IllegalArgumentException exceptionByColor = Assertions.assertThrows(IllegalArgumentException.class, () -> facultyServiceImpl.addFaculty(new Faculty("Стеблкрон", faculties.getFirst().getColor())));
        Assertions.assertEquals("Ошибка! Факультет с данным названием уже существует!", exceptionByName.getMessage());
        Assertions.assertEquals("Ошибка! Факультет с таким цветом уже существует!", exceptionByColor.getMessage());
    }

    @Test
    public void updateFaculty() {
        Faculty testFaculty = new Faculty(1L, "Стеблехвост", "Черный");
        when(facultyRepository.save(testFaculty)).thenReturn(testFaculty);
        when(facultyRepository.findById(1L)).thenReturn(Optional.ofNullable(faculties.get(1)));
        Assertions.assertEquals(testFaculty, facultyServiceImpl.updateFaculty(1L, new Faculty("Стеблехвост", "Черный")));
    }

    @Test
    public void updateFacultyWithExceptionByName() {
        Faculty testFacultyNameEquals = new Faculty(0L, faculties.getFirst().getName(), "Фиолетовый");
        List<Faculty> testFaculties = faculties.stream().
                filter(s -> s.getColor().equals(testFacultyNameEquals.getColor()) || s.getName().equals(testFacultyNameEquals.getName())).toList();
        when(facultyRepository.findById(0L)).thenReturn(Optional.ofNullable(faculties.getFirst())).thenReturn(Optional.of(testFacultyNameEquals));
        when(facultyRepository.findByNameOrColor(testFacultyNameEquals.getName(), "Красный")).thenReturn(testFaculties);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> facultyServiceImpl.updateFaculty(0L, new Faculty(testFacultyNameEquals.getName(), testFacultyNameEquals.getColor())));
        Assertions.assertEquals("Ошибка! Факультет с таким цветом уже существует!", illegalArgumentException.getMessage());
    }

    @Test
    public void updateFacultyWithExceptionByColor() {
        Faculty testFacultyColorEquals = new Faculty(0L, "Стеблкрон", faculties.getFirst().getColor());
        List<Faculty> testFaculties = faculties.stream().
                filter(s -> s.getColor().equals(testFacultyColorEquals.getColor()) || s.getName().equals(testFacultyColorEquals.getName())).toList();

        when(facultyRepository.findById(0L)).thenReturn(Optional.ofNullable(faculties.getFirst())).thenReturn(Optional.of(testFacultyColorEquals));
        when(facultyRepository.findByNameOrColor("Гриффиндор", "Красный")).thenReturn(testFaculties);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> facultyServiceImpl.updateFaculty(0L, new Faculty(testFacultyColorEquals.getName(), testFacultyColorEquals.getColor())));
        Assertions.assertEquals("Ошибка! Факультет с данным названием уже существует!", illegalArgumentException.getMessage());
    }

    @Test
    public void updateFacultyWithExceptionWhereAllMatch() {
        Faculty testFacultyAllMatch = new Faculty(0L, faculties.getFirst().getName(), faculties.getFirst().getColor());
        List<Faculty> testFaculties = faculties.stream().
                filter(s -> s.getColor().equals(testFacultyAllMatch.getColor()) || s.getName().equals(testFacultyAllMatch.getName())).toList();
        when(facultyRepository.findById(0L)).thenReturn(Optional.ofNullable(faculties.getFirst())).thenReturn(Optional.of(testFacultyAllMatch));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> facultyServiceImpl.updateFaculty(0L, new Faculty(testFacultyAllMatch.getName(), testFacultyAllMatch.getColor())));
        Assertions.assertEquals("Ошибка! Исходные данные равны изменяемым", illegalArgumentException.getMessage());
    }

    @Test
    public void deleteFacultyWithException() {
        when(facultyRepository.findById(0L)).thenReturn(Optional.empty());
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> facultyServiceImpl.deleteFaculty(0L));
        Assertions.assertEquals("Ошибка! Факультета с данным id не существует", exception.getMessage());
    }

}
