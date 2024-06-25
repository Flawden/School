package ru.hogwarts.school.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;

import java.io.IOException;
import java.util.List;

public interface AvatarRestApi {

    List<Avatar> getAvatars();

    Avatar getAvatarById(Long id);

    Avatar getAvatarByFilePathIgnoreCase(String filePath);

    List<Avatar> getAvatarByFileSize(Long fileSize);


    ResponseEntity<String> addAvatar(@PathVariable Long studentId, @RequestParam MultipartFile avatar) throws IOException;

    Avatar updateAvatar(Long id, MultipartFile updatedAvatar) throws IOException;

    void deleteAvatar(Long id);

    Long getNumberOfAvatars();

}
