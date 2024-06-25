package ru.hogwarts.school.dto;

import lombok.Getter;
import lombok.Setter;
import ru.hogwarts.school.model.Student;

@Getter
@Setter
public class AvatarDTO {

    private String filePath;
    private Long fileSize;
    private String mediaType;
    private byte[] data;
    private Student student;

}
