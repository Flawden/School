package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyService {

    List<Faculty> getFaculties();

    Faculty getByNameIgnoreCase(String name);

    Faculty getByColorIgnoreCase(String color);

    Faculty getFacultiesById(Long id);

    Faculty addFaculty(Faculty faculty);

    Faculty updateFaculty(Long id, Faculty faculty);

    void deleteFaculty(Long id);

}
