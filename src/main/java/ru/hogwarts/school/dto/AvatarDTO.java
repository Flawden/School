package ru.hogwarts.school.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.hogwarts.school.model.Student;

import java.util.Arrays;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvatarDTO avatarDTO = (AvatarDTO) o;
        return Objects.equals(id, avatarDTO.id) && Objects.equals(filePath, avatarDTO.filePath) && Objects.equals(fileSize, avatarDTO.fileSize) && Objects.equals(mediaType, avatarDTO.mediaType) && Objects.deepEquals(data, avatarDTO.data) && Objects.equals(student, avatarDTO.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filePath, fileSize, mediaType, Arrays.hashCode(data), student);
    }
}
