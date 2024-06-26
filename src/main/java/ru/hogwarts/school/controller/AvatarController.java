package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.controller.api.AvatarRestApi;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@RestController
@RequestMapping("/api/v1/avatars")
@RequiredArgsConstructor
@Tag(name="AvatarController", description="Предоставляет перечень аватарок и операций над ними")
public class AvatarController implements AvatarRestApi {

    private final AvatarService avatarService;

    @Override
    @GetMapping("/fromDisk/{id}")
    public Avatar getAvatarFromDiskById(Long avatarId) {
        return avatarService.getAvatarFromDiskById();
    }

    @Override
    @GetMapping
    public List<Avatar> getAvatars() {
        return avatarService.getAvatars();
    }

    @Override
    @GetMapping("/bySize/{fileSize}")
    public List<Avatar> getAvatarByFileSize(Long fileSize) {
        return avatarService.getAvatarByFileSize(fileSize);
    }

    @Override
    @PostMapping(value = "/{studentId}/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(Long studentId, MultipartFile avatar) throws IOException {
        if (avatar.getSize() > 1024 * 300) {
            return ResponseEntity.badRequest().body("File is too big");
        }
        avatarService.uploadAvatar(studentId, avatar);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/{id}")
    public void downloadAvatar(Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.getAvatarById(id);

        Path path = Path.of(avatar.getFilePath());

        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(path), 1024);
             BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream(), 1024);) {
                response.setContentType(avatar.getMediaType());
                response.setContentLength(Math.toIntExact(avatar.getFileSize()));
                bis.transferTo(bos);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteAvatar(Long id) {
        avatarService.deleteAvatar(id);
    }

    @Override
    @GetMapping("/count")
    public Long getNumberOfAvatars() {
        return avatarService.getNumberOfAvatars();
    }

}
