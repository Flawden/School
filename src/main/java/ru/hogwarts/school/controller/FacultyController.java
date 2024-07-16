package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.controller.api.FacultyRestApi;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/faculties")
@Tag(name="faculty", description="Предоставляет перечень факультетов и операций над ними")
public class FacultyController implements FacultyRestApi {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    @Override
    public List<Faculty> getFaculties() {
        return facultyService.getFaculties();
    }

    @Override
    public String getTheLongestNameOfFaculty() {
        return facultyService.getTheLongestNameOfFaculty();
    }

    @GetMapping("/name/{facultyName}")
    @Override
    public Faculty getByNameIgnoreCase(String facultyName) {
        return facultyService.getByNameIgnoreCase(facultyName);
    }

    @GetMapping("/color/{color}")
    @Override
    public Faculty getByColorIgnoreCase(String color) {
        return facultyService.getByColorIgnoreCase(color);
    }

    @Override
    @GetMapping("/by-faculty")
    public List<Student> getStudentsOfFaculty(Faculty faculty) {
        return  facultyService.getStudentsOfFaculty(faculty);

    }

    @GetMapping("/{id}")
    @Override
    public Faculty getFacultiesById(Long id) {
        return facultyService.getFacultiesById(id);
    }

    @PostMapping
    @Override
    public Faculty addFaculty(Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @PatchMapping("/{name}")
    @Override
    public Faculty updateFaculty(String name, Faculty faculty) {
        return facultyService.updateFaculty(name, faculty);
    }

    @DeleteMapping("/{id}")
    @Override
    public void deleteFaculty(Long id) {
        facultyService.deleteFaculty(id);
    }


}
