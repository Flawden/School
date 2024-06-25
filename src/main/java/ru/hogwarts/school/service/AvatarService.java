package ru.hogwarts.school.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;

import java.io.IOException;
import java.util.List;

public interface AvatarService {

    List<Avatar> getAvatars();

    Avatar getAvatarById(Long id);

    Avatar getAvatarByFilePathIgnoreCase(String filePath);

    List<Avatar> getAvatarByFileSize(Long fileSize);


    Avatar addAvatar(Long studentId, MultipartFile avatar) throws IOException;

    Avatar updateAvatar(Long studentId, MultipartFile updatedAvatar) throws IOException;

    void deleteAvatar(Long id);

    Long getNumberOfAvatars();

}
