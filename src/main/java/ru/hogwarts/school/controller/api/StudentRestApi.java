package ru.hogwarts.school.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRestApi {

    @Operation(
            summary = "Найти всех студентов",
            description = "Позволяет совершить поиск всех студентов"
    )
    List<Student> getStudents();

    @Operation(
            summary = "Найти студента по имени",
            description = "Позволяет совершить поиск всех студентов по имени"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Ошибка! Студентов с данным именем не найдено."),
            @ApiResponse(responseCode = "200", description = "OK")
    })
    List<Student> getStudentsByName(@PathParam("name") String name);

    @Operation(
            summary = "Найти студентов по возрасту",
            description = "Позволяет совершить поиск всех студентов по указанному возрасту"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Ошибка! Студентов с данным возврастом не найдено."),
            @ApiResponse(responseCode = "200", description = "OK")
    })
    List<Student> getStudentsByAge(@PathParam("age") Integer age);

    @Operation(
            summary = "Найти студента по Id",
            description = "Позволяет совершить поиск студента по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Ошибка! Студентов с данным id не найдено."),
            @ApiResponse(responseCode = "200", description = "OK")
    })
    Student getStudentsById(@PathParam("id") Long id);

    @PostMapping
    @Operation(
            summary = "Добавить студента",
            description = "Записать имя человека в студенты школы магии и волшебства"
    )
    Student addStudent(@RequestBody Student student);

    @Operation(
            summary = "Обновить студента",
            description = "Переписать биографию студента с чистого листа"
    )
    Student updateStudents(@PathParam("id") Long id, @RequestBody Student student);

    @Operation(
            summary = "Удалить студента",
            description = "Стереть имя студента с лица земли и вычеркнуть из учебников истории"
    )
    void deleteStudent(@PathParam("id") Long id);

}
