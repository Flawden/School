package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.controller.api.FacultyRestApi;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/faculties")
@Tag(name = "faculty", description = "Предоставляет перечень факультетов и операций над ними")
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
    public Faculty getByNameIgnoreCase(@PathVariable("facultyName") String facultyName) {
        return facultyService.getByNameIgnoreCase(facultyName);
    }

    @GetMapping("/color/{color}")
    @Override
    public Faculty getByColorIgnoreCase(@PathVariable("color") String color) {
        return facultyService.getByColorIgnoreCase(color);
    }

    @Override
    @GetMapping("/by-faculty")
    public List<Student> getStudentsOfFaculty(@RequestBody Faculty faculty) {
        return facultyService.getStudentsOfFaculty(faculty);

    }

    @GetMapping("/{id}")
    @Override
    public Faculty getFacultiesById(@PathVariable("id") Long id) {
        return facultyService.getFacultiesById(id);
    }

    @PostMapping
    @Override
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @PatchMapping("/{name}")
    @Override
    public Faculty updateFaculty(@PathVariable("name") String name, @RequestBody Faculty faculty) {
        return facultyService.updateFaculty(name, faculty);
    }

    @DeleteMapping("/{id}")
    @Override
    public void deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }

}
