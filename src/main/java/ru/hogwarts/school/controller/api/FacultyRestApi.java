package ru.hogwarts.school.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyRestApi {

    @Operation(
            summary = "Получить все факультеты",
            description = "Позволяет получить все факультеты"
    )
    List<Faculty> getFaculties();


    @Operation(
            summary = "Получить факультет по названию",
            description = "Позволяет получить факультет по названию"
    )
    Faculty getFacultiesByName(@RequestParam String facultyName);


    @Operation(
            summary = "Получить факультет по цвету",
            description = "Позволяет получить факультет по цвету"
    )
    Faculty getFacultiesByColor(@RequestParam String color);


    @Operation(
            summary = "Получить факультет по id",
            description = "Позволяет получить факультет по id"
    )
    Faculty getFacultiesById(@RequestParam Long id);

    @Operation(
            summary = "Добавить факультет",
            description = "Позволяет добавить факультет"
    )
    Faculty addFaculty(@RequestParam String name, @RequestParam String color);


    @Operation(
            summary = "Исправить данные факультета",
            description = "Позволяет изменить данные факультета"
    )
    Faculty updateFaculty(@RequestParam Long id, @RequestParam String name, @RequestParam String color);

    @Operation(
            summary = "Уничтожить факультета",
            description = "Позволяет удалить опорочивший свою честь факультет с лица человечества"
    )
    void deleteFaculty(@RequestParam Long id);

}
