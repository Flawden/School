package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/faculty")
@Tag(name="FacultyController", description="Предоставляет перечень факультетов и операций над ними")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    @Operation(
            summary = "Получить все факультеты",
            description = "Позволяет получить все факультеты"
    )
    public HashMap<Long, Faculty> getFaculties() {
        return facultyService.getFaculties();
    }

    @GetMapping("/name")
    @Operation(
            summary = "Получить факультет по названию",
            description = "Позволяет получить факультет по названию"
    )
    public Faculty getFacultiesByName(@RequestParam String facultyName) {
        return facultyService.getFacultiesByName(facultyName);
    }

    @GetMapping("/color")
    @Operation(
            summary = "Получить факультет по цвету",
            description = "Позволяет получить факультет по цвету"
    )
    public Faculty getFacultiesByColor(@RequestParam String color) {
        return facultyService.getFacultiesByColor(color);
    }

    @GetMapping("/id")
    @Operation(
            summary = "Получить факультет по id",
            description = "Позволяет получить факультет по id"
    )
    public Faculty getFacultiesById(@RequestParam Long id) {
        return facultyService.getFacultiesById(id);
    }

    @PostMapping
    @Operation(
            summary = "Добавить факультет",
            description = "Позволяет добавить факультет"
    )
    public Faculty addFaculty(@RequestParam String name, @RequestParam String color) {
        return facultyService.addFaculty(name, color);
    }

    @PatchMapping
    @Operation(
            summary = "Исправить данные факультета",
            description = "Позволяет изменить данные факультета"
    )
    public Faculty updateFaculty(@RequestParam Long id, @RequestParam String name, @RequestParam String color) {
        return facultyService.updateFaculty(id, name, color);
    }

    @DeleteMapping
    @Operation(
            summary = "Уничтожить факультета",
            description = "Позволяет удалить опорочивший свою честь факультет с лица человечества"
    )
    public void deleteFaculty(@RequestParam Long id) {
        facultyService.deleteFaculty(id);
    }
}
