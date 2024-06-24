package ru.hogwarts.school.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.hogwarts.school.model.Faculty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentWithFacultyDTO {

    private String name;

    private Integer age;

    private Long studentIdNumber;

    private FacultyDTO faculty;

}
