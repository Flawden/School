package ru.hogwarts.school.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.hogwarts.school.model.Student;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvatarDTO {

    private Long id;

    private String filePath;

    private Long fileSize;

    private String mediaType;

    private byte[] data;

    private StudentDTO student;


}
