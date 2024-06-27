package ru.hogwarts.school.controller.api;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.dto.AvatarDTO;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;

public interface AvatarRestApi {

    AvatarDTO getAvatarFromDBById(@PathVariable Long avatarId);


    ResponseEntity<String> uploadAvatar(@PathVariable Long studentId, @RequestParam MultipartFile avatar) throws IOException;

    void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException;

    void deleteAvatar(Long id);

    Long getNumberOfAvatars();

}
