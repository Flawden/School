package ru.hogwarts.school.service;

import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface FacultyService {

    List<Faculty> getFaculties();

    Faculty getFacultyById(Long id);

    Faculty getByNameIgnoreCase(String name);

    Faculty getByColorIgnoreCase(String color);

    Faculty getFacultiesById(Long id);

    Faculty addFaculty(Faculty faculty);

    Faculty updateFaculty(String name, Faculty faculty);

    void deleteFaculty(String name);

    Faculty addStudentToFacultyById(Long studentIdNumber, String facultyName);

    Long getNumberOfFaculties();

    List<Student> getStudentsOfFaculty(Faculty faculty);
}
