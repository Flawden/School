package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyService {

    List<Faculty> getFaculties();

    Faculty getFacultyByName(String name);

    Faculty getFacultiesByColor(String color);

    Faculty getFacultiesById(Long id);

    Faculty addFaculty(Faculty faculty);

    Faculty updateFaculty(Long id, Faculty faculty);

    void deleteFaculty(Long id);

}
