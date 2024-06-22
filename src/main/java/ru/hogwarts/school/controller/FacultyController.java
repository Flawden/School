package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.controller.api.FacultyRestApi;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/faculties")
@RequiredArgsConstructor
@Tag(name="FacultyController", description="Предоставляет перечень факультетов и операций над ними")
public class FacultyController implements FacultyRestApi {

    private final FacultyService facultyService;
    private final ModelMapper mapper;

    @GetMapping
    @Override
    public List<FacultyDTO> getFaculties() {
        List<Faculty> faculties = facultyService.getFaculties();
        List<FacultyDTO> facultyDTOS = new ArrayList<>();
        for(Faculty faculty: faculties) {
            facultyDTOS.add(mapper.map(faculty, FacultyDTO.class));
        }
        return facultyDTOS;
    }

    @GetMapping("/name/{facultyName}")
    @Override
    public FacultyDTO getByNameIgnoreCase(String facultyName) {
        return mapper.map(facultyService.getByNameIgnoreCase(facultyName), FacultyDTO.class);
    }

    @GetMapping("/color/{color}")
    @Override
    public FacultyDTO getByColorIgnoreCase(String color) {
        return mapper.map(facultyService.getByColorIgnoreCase(color), FacultyDTO.class);
    }

    @Override
    public List<StudentDTO> getStudentsOfFaculty(FacultyDTO faculty) {
        List<Student> students = facultyService.getStudentsOfFaculty(mapper.map(faculty, Faculty.class));
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student student: students) {
            studentDTOS.add(mapper.map(student, StudentDTO.class));
        }
        return studentDTOS;
    }

    @GetMapping("/{id}")
    @Override
    public FacultyDTO getFacultiesById(Long id) {
        return mapper.map(facultyService.getFacultiesById(id), FacultyDTO.class);
    }

    @PostMapping
    @Override
    public FacultyDTO addFaculty(FacultyDTO faculty) {
        return mapper.map(facultyService.addFaculty(mapper.map(faculty, Faculty.class)), FacultyDTO.class);
    }

    @PatchMapping("/{id}")
    @Override
    public FacultyDTO updateFaculty(Long id, FacultyDTO faculty) {
        return mapper.map(facultyService.updateFaculty(id, mapper.map(faculty, Faculty.class)), FacultyDTO.class);
    }

    @DeleteMapping("/{id}")
    @Override
    public void deleteFaculty(Long id) {
        facultyService.deleteFaculty(id);
    }
}
