package ru.hogwarts.school.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    List<Student> getStudentsByName(String name);

    @Operation(
            summary = "Найти студентов по возрасту",
            description = "Позволяет совершить поиск всех0 студентов по указанному возрасту"
    )
    List<Student> getStudentsByAge(Integer age);

    @Operation(
            summary = "Найти студента по Id",
            description = "Позволяет совершить поиск студента по ID"
    )
    Student getStudentsById(Long id);

    @PostMapping
    @Operation(
            summary = "Добавить студента",
            description = "Записать имя человека в студенты школы магии и волшебства"
    )
    Student addStudents(String name, Integer age);

    @Operation(
            summary = "Обновить студента",
            description = "Переписать биографию студента с чистого листа"
    )
    Student updateStudents(Long id, Student student);

    @Operation(
            summary = "Удалить студента",
            description = "Стереть имя студента с лица земли и вычеркнуть из учебников истории"
    )
    void deleteStudent(Long id);

}
