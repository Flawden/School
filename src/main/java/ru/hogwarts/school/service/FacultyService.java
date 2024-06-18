package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {

    private FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public List<Faculty> getFaculties() {
        return facultyRepository.findAll();
    }

    public Faculty getFacultyByName(String name) {
        Optional<Faculty> faculty = facultyRepository.getFacultyByName(name);
            if (faculty.isPresent()) {
                return faculty.get();
            }
        throw new IllegalArgumentException("Ошибка! Факультета с данным названием не существует");
    }

    public Faculty getFacultiesByColor(String color) {
        Optional<Faculty> faculty = facultyRepository.getFacultyByColor(color);
            if (faculty.isPresent()) {
                return faculty.get();
            }

        throw new IllegalArgumentException("Ошибка! Факультета с данным цветом не существует");
    }

    public Faculty getFacultiesById(Long id) {
        Optional<Faculty> faculty = facultyRepository.getFacultyById(id);
        if (faculty.isEmpty()) {
            throw new IllegalArgumentException("Ошибка! Факультета с данным id не существует");
        }
        return faculty.get();
    }

    public Faculty addFaculty(String name, String color) {
        Faculty faculty = new Faculty(name, color);
        checkFacultyToDuplicate(faculty);
        return facultyRepository.save(faculty);
    }

    private void checkFacultyToDuplicate(Faculty faculty) {
        Optional<Faculty> isFacultyExist = facultyRepository.getFacultyByName(faculty.getName());
            if (isFacultyExist.isPresent()) {
                throw new IllegalArgumentException("Ошибка! Факультет с данным названием уже существует!");
            }
       isFacultyExist = facultyRepository.getFacultyByColor(faculty.getColor());
            if (isFacultyExist.isPresent()) {
                throw new IllegalArgumentException("Ошибка! Факультет с таким цветом уже существует!");
            }

    }

    public Faculty updateFaculty(Long id, String name, String color) {
        Optional<Faculty> faculty = facultyRepository.getFacultyById(id);
        if(faculty.isEmpty()) {
            throw new IllegalArgumentException("Ошибка! Факультета с данным id не существует");
        }
        faculty.get().setName(name);
        faculty.get().setColor(color);
        return facultyRepository.save(faculty.get());
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

}