package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.controller.api.AvatarRestApi;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/avatars")
@RequiredArgsConstructor
@Tag(name="AvatarController", description="Предоставляет перечень аватарок и операций над ними")
public class AvatarController implements AvatarRestApi {

    private final AvatarService avatarService;

    @Override
    public List<Avatar> getAvatars() {
        return avatarService.getAvatars();
    }

    @Override
    public Avatar getAvatarById(Long id) {
        return avatarService.getAvatarById(id);
    }

    @Override
    public Avatar getAvatarByFilePathIgnoreCase(String filePath) {
        return avatarService.getAvatarByFilePathIgnoreCase(filePath);
    }

    @Override
    public List<Avatar> getAvatarByFileSize(Long fileSize) {
        return avatarService.getAvatarByFileSize(fileSize);
    }

    @Override
    @PostMapping(value = "/{studentId}/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addAvatar(Long studentId, MultipartFile avatar) throws IOException {
        if (avatar.getSize() > 1024 * 300) {
            return ResponseEntity.badRequest().body("File is too big");
        }
        avatarService.addAvatar(studentId, avatar);
        return ResponseEntity.ok().build();
    }

    @Override
    public Avatar updateAvatar(Long id, MultipartFile updatedAvatar) throws IOException {
        return avatarService.updateAvatar(id, updatedAvatar);
    }

    @Override
    public void deleteAvatar(Long id) {
        avatarService.deleteAvatar(id);
    }

    @Override
    public Long getNumberOfAvatars() {
        return avatarService.getNumberOfAvatars();
    }

}
