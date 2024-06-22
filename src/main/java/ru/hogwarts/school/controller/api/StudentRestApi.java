package ru.hogwarts.school.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.hogwarts.school.dto.StudentDTO;
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
    List<Student> findByNameIgnoreCase(@PathVariable("name") String name);

    @Operation(
            summary = "Найти студентов по возрасту",
            description = "Позволяет совершить поиск всех студентов по указанному возрасту"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Ошибка! Студентов с данным возврастом не найдено."),
            @ApiResponse(responseCode = "200", description = "OK")
    })
    List<Student> getStudentsByAge(@PathVariable Integer age);

    @Operation(
            summary = "Получить студентов по диапазону возврастов",
            description = "Получить всех студентов, чей диапазов возврастов попадает в заданные значения"
    )
    List<Student> findByAgeBetween(@PathVariable("min") Integer min, @PathVariable("max") Integer max);

    @Operation(
            summary = "Найти студента по Id",
            description = "Позволяет совершить поиск студента по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Ошибка! Студентов с данным id не найдено."),
            @ApiResponse(responseCode = "200", description = "OK")
    })
    Student getStudentsById(@PathVariable("id") Long id);

    @PostMapping
    @Operation(
            summary = "Добавить студента",
            description = "Записать имя человека в студенты школы магии и волшебства"
    )
    Student addStudent(@RequestBody StudentDTO student);

    @Operation(
            summary = "Обновить студента",
            description = "Переписать биографию студента с чистого листа"
    )
    Student updateStudents(@PathVariable("id") Long id, @RequestBody StudentDTO student);

    @Operation(
            summary = "Удалить студента",
            description = "Стереть имя студента с лица земли и вычеркнуть из учебников истории"
    )
    void deleteStudent(@PathVariable("id") Long id);

}
