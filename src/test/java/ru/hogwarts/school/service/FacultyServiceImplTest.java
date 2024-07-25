package ru.hogwarts.school.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.FacultySaveException;
import ru.hogwarts.school.exception.FacultyUpdateException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
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
    @Mock
    private StudentRepository studentRepository;

    private static List<Faculty> faculties;

    @BeforeEach
    public void setUp() {
        facultyServiceImpl = new FacultyServiceImpl(facultyRepository, studentRepository);
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
        when(facultyRepository.getByNameIgnoreCase(faculties.getFirst().getName())).thenReturn(testFaculty);
        Assertions.assertEquals(faculties.getFirst(), facultyServiceImpl.getByNameIgnoreCase(faculties.getFirst().getName()));
    }

    @Test
    public void getFacultiesByNameWithException() {
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> facultyServiceImpl.getByNameIgnoreCase("Фламма"));
        Assertions.assertEquals("Ошибка! Факультета с данным названием не существует", exception.getMessage());
    }

    @Test
    public void getFacultiesByColor() {
        when(facultyRepository.getByColorIgnoreCase(faculties.getFirst().getColor())).thenReturn(Optional.ofNullable(faculties.getFirst()));
        Assertions.assertEquals(faculties.getFirst(), facultyServiceImpl.getByColorIgnoreCase(faculties.getFirst().getColor()));
    }

    @Test
    public void getFacultiesByColorWithException() {
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> facultyServiceImpl.getByColorIgnoreCase("Радужный"));
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
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> facultyServiceImpl.getFacultiesById(faculties.size() + 1L));
        Assertions.assertEquals("Ошибка! Факультета с данным id не существует", exception.getMessage());
    }

    @Test
    public void addFaculty() {
        Faculty testFaculty = new Faculty(0L, "Стеблхвост", "Черный");
        when(facultyRepository.save(testFaculty)).thenReturn(testFaculty);
        Assertions.assertEquals(testFaculty, facultyServiceImpl.addFaculty(testFaculty));
    }

    @Test
    public void addFacultyWithExceptionsByName() {
        Faculty testFaculty = new Faculty(faculties.getFirst().getName(), "Серобуромалиновый");
        when(facultyRepository.save(testFaculty)).thenThrow(new FacultySaveException("Ошибка! Факультет с переданными именем или цветом уже существуют"));
        FacultySaveException exceptionByName = Assertions.assertThrows(FacultySaveException.class, () -> facultyServiceImpl.addFaculty(testFaculty));
        Assertions.assertEquals("Ошибка! Факультет с переданными именем или цветом уже существуют", exceptionByName.getMessage());

    }

    @Test
    public void addFacultyWithExceptionsByColor() {
        Faculty testFaculty = new Faculty("Лисички", faculties.getFirst().getColor());
        when(facultyRepository.save(testFaculty)).thenThrow(new FacultySaveException("Ошибка! Факультет с переданными именем или цветом уже существуют"));
        FacultySaveException exceptionByName = Assertions.assertThrows(FacultySaveException.class, () -> facultyServiceImpl.addFaculty(testFaculty));
        Assertions.assertEquals("Ошибка! Факультет с переданными именем или цветом уже существуют", exceptionByName.getMessage());

    }

    @Test
    public void updateFaculty() {
        Faculty testFaculty = new Faculty(1L, "Стеблехвост", "Черный");
        when(facultyRepository.save(testFaculty)).thenReturn(testFaculty);
        when(facultyRepository.findById(1L)).thenReturn(Optional.ofNullable(faculties.get(1)));
        Assertions.assertEquals(testFaculty, facultyServiceImpl.updateFaculty("Стеблехвост", new Faculty("Стеблехвост", "Черный")));
    }


    @Test
    public void updateFacultyWithExceptionCantSave() {
        Faculty testFacultyAllMatch = new Faculty(0L, faculties.getFirst().getName(), faculties.getFirst().getColor());
        when(facultyRepository.findById(0L)).thenReturn(Optional.ofNullable(faculties.getFirst()));
        when(facultyRepository.save(testFacultyAllMatch)).thenThrow(new FacultyUpdateException("Ошибка! Факультет с переданными именем или цветом уже существуют"));
        FacultyUpdateException exception = Assertions.assertThrows(FacultyUpdateException.class, () -> facultyServiceImpl.updateFaculty(faculties.getFirst().getName(), new Faculty(testFacultyAllMatch.getName(), testFacultyAllMatch.getColor())));
        Assertions.assertEquals("Ошибка! Факультет с переданными именем или цветом уже существуют", exception.getMessage());
    }

    @Test
    public void updateFacultyWithExceptionCantFind() {
        when(facultyRepository.findById(990L)).thenThrow(new EntityNotFoundException("Ошибка! Факультета с данным id не существует"));
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> facultyServiceImpl.updateFaculty("Блабладор", new Faculty(faculties.getFirst().getName(), faculties.getFirst().getColor())));
        Assertions.assertEquals("Ошибка! Факультета с данным id не существует", exception.getMessage());
    }

    @Test
    public void deleteFacultyWithException() {
        when(facultyRepository.findById(0L)).thenReturn(Optional.empty());
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> facultyServiceImpl.deleteFaculty(990L));
        Assertions.assertEquals("Ошибка! Факультета с данным названием не существует", exception.getMessage());
    }

}
