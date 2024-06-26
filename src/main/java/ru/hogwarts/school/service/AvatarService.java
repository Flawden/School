package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarService {

    List<Avatar> getAvatars();

    Avatar getAvatarById(Long id);

    List<Avatar> getAvatarByFileSize(Long fileSize);

    void uploadAvatar(Long studentId, MultipartFile avatar) throws IOException;

    void deleteAvatar(Long id);

    Long getNumberOfAvatars();

}
