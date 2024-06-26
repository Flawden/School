package ru.hogwarts.school.controller.api;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarRestApi {

    Avatar getAvatarFromDiskById(@PathVariable Long avatarId);

    List<Avatar> getAvatars();

    List<Avatar> getAvatarByFileSize(Long fileSize);


    ResponseEntity<String> uploadAvatar(@PathVariable Long studentId, @RequestParam MultipartFile avatar) throws IOException;

    void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException;

    void deleteAvatar(Long id);

    Long getNumberOfAvatars();

}
