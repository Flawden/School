package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyService {

    List<Faculty> getFaculties();

    Faculty getFacultyByName(String name);

    Faculty getFacultiesByColor(String color);

    Faculty getFacultiesById(Long id);

    Faculty addFaculty(String name, String color);

    Faculty updateFaculty(Long id, String name, String color);

    void deleteFaculty(Long id);

}
