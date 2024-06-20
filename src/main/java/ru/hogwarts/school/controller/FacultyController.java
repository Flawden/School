package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.controller.api.FacultyRestApi;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/faculties")
@RequiredArgsConstructor
@Tag(name="FacultyController", description="Предоставляет перечень факультетов и операций над ними")
public class FacultyController implements FacultyRestApi {

    private final FacultyService facultyService;

    @GetMapping
    public List<Faculty> getFaculties() {
        return facultyService.getFaculties();
    }

    @GetMapping("/name/{facultyName}")
    public Faculty getByNameIgnoreCase(String facultyName) {
        return facultyService.getByNameIgnoreCase(facultyName);
    }

    @GetMapping("/color/{color}")
    public Faculty getByColorIgnoreCase(String color) {
        return facultyService.getByColorIgnoreCase(color);
    }

    @GetMapping("/{id}")
    public Faculty getFacultiesById(Long id) {
        return facultyService.getFacultiesById(id);
    }

    @PostMapping
    public Faculty addFaculty(Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @PatchMapping("/{id}")
    public Faculty updateFaculty(Long id, Faculty faculty) {
        return facultyService.updateFaculty(id, faculty);
    }

    @DeleteMapping("/{id}")
    public void deleteFaculty(Long id) {
        facultyService.deleteFaculty(id);
    }
}
