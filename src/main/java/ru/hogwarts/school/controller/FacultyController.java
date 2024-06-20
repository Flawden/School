package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
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
    public Faculty getFacultiesByName(String facultyName) {
        return facultyService.getFacultyByName(facultyName);
    }

    @GetMapping("/color/{color}")
    public Faculty getFacultiesByColor(String color) {
        return facultyService.getFacultiesByColor(color);
    }

    @GetMapping("/id/{id}")
    public Faculty getFacultiesById(Long id) {
        return facultyService.getFacultiesById(id);
    }

    @PostMapping
    public Faculty addFaculty(Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @PatchMapping("/id/{id}")
    public Faculty updateFaculty(Long id, Faculty faculty) {
        return facultyService.updateFaculty(id, faculty);
    }

    @DeleteMapping("/id/{id}")
    public void deleteFaculty(Long id) {
        facultyService.deleteFaculty(id);
    }
}
