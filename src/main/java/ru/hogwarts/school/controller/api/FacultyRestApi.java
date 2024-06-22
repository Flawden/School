package ru.hogwarts.school.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface FacultyRestApi {

    @Operation(
            summary = "Получить все факультеты",
            description = "Позволяет получить все факультеты"
    )
    List<FacultyDTO> getFaculties();

    @Operation(
            summary = "Получить факультет по названию",
            description = "Позволяет получить факультет по названию"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Ошибка! Студента с данным именем не найдено"),
            @ApiResponse(responseCode = "200", description = "OK")
    })
    FacultyDTO getByNameIgnoreCase(@PathVariable("facultyName") String facultyName);

    @Operation(
            summary = "Получить факультет по цвету",
            description = "Позволяет получить факультет по цвету"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Ошибка! Студента с данным цветом не найдено"),
            @ApiResponse(responseCode = "200", description = "OK")
    })
    FacultyDTO getByColorIgnoreCase(@PathVariable("color") String color);


    @Operation(
            summary = "Получить факультет по id",
            description = "Позволяет получить факультет по id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Ошибка! Факультета с данным id не найдено"),
            @ApiResponse(responseCode = "200", description = "OK")
    })
    FacultyDTO getFacultiesById(@PathVariable("id") Long id);

    @Operation(
            summary = "Добавить факультет",
            description = "Позволяет добавить факультет"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Ошибка! Факультета с переданными именем или цветом уже существуют"),
            @ApiResponse(responseCode = "200", description = "OK")
    })
    FacultyDTO addFaculty(@RequestBody FacultyDTO faculty);

    @Operation(
            summary = "Исправить данные факультета",
            description = "Позволяет изменить данные факультета"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Ошибка! Факультета с переданными именем или цветом уже существуют"),
            @ApiResponse(responseCode = "200", description = "OK")
    })
    FacultyDTO updateFaculty(@PathVariable("id") Long id, @RequestBody FacultyDTO faculty);

    @Operation(
            summary = "Уничтожить факультета",
            description = "Позволяет удалить опорочивший свою честь факультет с лица человечества"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Ошибка! Факультета с данным id не существует"),
            @ApiResponse(responseCode = "200", description = "OK")
    })
    void deleteFaculty(@PathVariable Long id);

}
