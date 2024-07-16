package ru.hogwarts.school.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRestApi {

    @Operation(
            summary = "Найти всех студентов",
            description = "Позволяет совершить поиск всех студентов"
    )
    List<Student> getStudents();

    @Operation(
            summary = "Найти имена всех студентов с указанной буквы",
            description = "Позволяет найти имена всех студентов с указанной буквы"
    )
    List<String> getStudentsWhoseNameStartsWith(@PathVariable String startWith);

    @Operation(
            summary = "Получить 6 студентов",
            description = "Эндпоинт, для выполнения задания №4.6.1"
    )
    void getSixStudentsByParallel();

    @Operation(
            summary = "Получить 6 студентов",
            description = "Эндпоинт, для выполнения задания №4.6.2"
    )
    void getSixStudentsByParallelWithSynchronized();

    @Operation(
            summary = "Получить число студентов",
            description = "Позволяет получить число студентов"
    )
    Integer getCountOfStudents();

    @Operation(
            summary = "Получить средний возвраст студентов",
            description = "Позволяет получить средний возвраст студентов"
    )
    Double getAverageAgeOfStudents();

    @Operation(
            summary = "Получить средний возвраст студентов",
            description = "Позволяет получить средний возвраст студентов (Эндпоинт выполнен в целях решения домашнего задания)"
    )
    Double getAverageAgeOfStudentsWithStreamApi();

    @Operation(
            summary = "Получить 5 последних студентов",
            description = "Позволяет 5 последнихо студентов"
    )
    List<Student> getLastFiveStudents();

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
            summary = "Найти факультет студента",
            description = "Позволяет совершить поиск факультета по студенту"
    )
    Faculty getFacultyOfStudent(@RequestBody Student student);

    @Operation(
            summary = "Получить студентов по диапазону возврастов",
            description = "Получить всех студентов, чей диапазов возврастов попадает в заданные значения"
    )
    List<Student> findByAgeBetween(@RequestParam(defaultValue = "0") Integer min, @RequestParam(defaultValue = "0") Integer max);

    @Operation(
            summary = "Найти студента по Id",
            description = "Позволяет совершить поиск студента по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Ошибка! Студентов с данным id не найдено."),
            @ApiResponse(responseCode = "200", description = "OK")
    })
    Student getStudentById(@PathVariable("id") Long id);

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
    Student updateStudents(@PathVariable("id") Long id, @RequestBody Student student);

    @Operation(
            summary = "Удалить студента",
            description = "Стереть имя студента с лица земли и вычеркнуть из учебников истории"
    )
    void deleteStudent(@PathVariable("id") Long id);

}
