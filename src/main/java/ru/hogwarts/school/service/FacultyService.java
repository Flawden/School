package ru.hogwarts.school.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.Optional;

@Service
public class FacultyService {

    public FacultyService() {
        faculties = new HashMap<>();
        faculties.put(0L, new Faculty(0L, "Гриффиндор", "Красный"));
        faculties.put(1L, new Faculty(1L, "Слизерин", "Зеленый"));
        faculties.put(2L, new Faculty(2L, "Пуффендуй", "Желтый"));
        faculties.put(3L, new Faculty(3L, "Когтевран", "Синий"));
        faculties.put(4L, new Faculty(4L, "Волжский политехнический техникум", "Серый"));
    }

    private static HashMap<Long, Faculty> faculties;

    public HashMap<Long, Faculty> getFaculties() {
        return faculties;
    }

    public Faculty getFacultiesByName(String name) {
        for (Faculty faculty: faculties.values()) {
            if (faculty.getName().equals(name)) {
                return faculty;
            }
        }
        throw new IllegalArgumentException("Ошибка! Факультета с данным названием не существует");
    }

    public Faculty getFacultiesByColor(String color) {
        for (Faculty faculty: faculties.values()) {
            if (faculty.getColor().equals(color)) {
                return faculty;
            }
        }
        throw new IllegalArgumentException("Ошибка! Факультета с данным цветом не существует");
    }

    public Faculty getFacultiesById(Long id) {
        Optional<Faculty> faculty = Optional.ofNullable(faculties.get(id));
        if (faculty.isEmpty()) {
            throw new IllegalArgumentException("Ошибка! Факультета с данным id не существует");
        }
        return faculty.get();
    }

    public Faculty addFaculty(String name, String color) {
        Faculty faculty = new Faculty(faculties.size() + 1L, name, color);
        checkFacultyToDuplicate(faculty);
        faculties.put(faculties.size() + 1L, faculty);
        return faculties.get((long) faculties.size());
    }

    private void checkFacultyToDuplicate(Faculty faculty) {
        for (Faculty ExistingFaculty: faculties.values()) {
            if (ExistingFaculty.getName().equals(faculty.getName())) {
                throw new IllegalArgumentException("Ошибка! Факультет с данным названием уже существует!");
            }
            if (ExistingFaculty.getColor().equals(faculty.getColor())) {
                throw new IllegalArgumentException("Ошибка! Факультет с таким цветом уже существует!");
            }
        }
    }

    public Faculty updateFaculty(Long id, String name, String color) {
        Faculty faculty = new Faculty(id, name, color);
        faculties.put(id, faculty);
        return faculties.get(id);
    }

    public void deleteFaculty(Long id) {
        faculties.remove(id);
    }

}