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

    @GetMapping("/name")
    public Faculty getFacultiesByName(@RequestParam String facultyName) {
        return facultyService.getFacultyByName(facultyName);
    }

    @GetMapping("/color")
    public Faculty getFacultiesByColor(@RequestParam String color) {
        return facultyService.getFacultiesByColor(color);
    }

    @GetMapping("/id")
    public Faculty getFacultiesById(@RequestParam Long id) {
        return facultyService.getFacultiesById(id);
    }

    @PostMapping
    public Faculty addFaculty(@RequestParam String name, @RequestParam String color) {
        return facultyService.addFaculty(name, color);
    }

    @PatchMapping
    public Faculty updateFaculty(@RequestParam Long id, @RequestParam String name, @RequestParam String color) {
        return facultyService.updateFaculty(id, name, color);
    }

    @DeleteMapping
    public void deleteFaculty(@RequestParam Long id) {
        facultyService.deleteFaculty(id);
    }
}
