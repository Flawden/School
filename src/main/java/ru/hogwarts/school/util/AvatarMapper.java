package ru.hogwarts.school.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.hogwarts.school.dto.AvatarDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;

@Component
public class AvatarMapper {

    private final ModelMapper mapper;

    public AvatarMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public AvatarDTO avatarToAvatarDTO(Avatar avatar) {
        return new AvatarDTO(avatar.getId(), avatar.getFilePath(), avatar.getFileSize(), avatar.getMediaType(), avatar.getData(), mapper.map(avatar.getStudent(), StudentDTO.class));
    }

    public Avatar avatarDTOToAvatar(AvatarDTO avatarDTO) {
        return new Avatar(avatarDTO.getId(), avatarDTO.getFilePath(), avatarDTO.getFileSize(), avatarDTO.getMediaType(), avatarDTO.getData(), mapper.map(avatarDTO.getStudent(), Student.class));
    }
}
