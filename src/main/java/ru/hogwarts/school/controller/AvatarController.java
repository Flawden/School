package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.annotation.LogNameOfRunningMethod;
import ru.hogwarts.school.controller.api.AvatarRestApi;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/avatars")
@RequiredArgsConstructor
@Tag(name = "avatar", description = "Предоставляет перечень аватарок и операций над ними")
@LogNameOfRunningMethod
public class AvatarController implements AvatarRestApi {

    private final AvatarService avatarService;

    @Override
    @GetMapping("/from-db/{avatarId}")
    public Avatar getAvatarFromDB(@PathVariable Long avatarId) {
        return avatarService.getAvatarById(avatarId);
    }

    @Override
    @GetMapping("/with-pagination")
    public ResponseEntity<?> getAvatarsFromDBWithPagination(@RequestParam(defaultValue = "0") Integer numberOfPage, @RequestParam(defaultValue = "1") Integer sizeOfPage, HttpServletResponse response) throws IOException {
        List<Avatar> avatars = avatarService.getAvatarsFromDBWithPagination(numberOfPage, sizeOfPage);

        if (avatars.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ResponseEntity<byte[]>> responses = new ArrayList<>();

        for (Avatar avatar : avatars) {
            Path path = Path.of(avatar.getFilePath());
            byte[] content = Files.readAllBytes(path);

            responses.add(ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(avatar.getMediaType()))
                    .contentLength(avatar.getFileSize())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path.getFileName().toString() + "\"")
                    .body(content));
        }

        return ResponseEntity.ok(responses);
    }

    @Override
    @PostMapping(value = "/{studentId}/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() > 1024 * 300) {
            return ResponseEntity.badRequest().body("File is too big");
        }
        avatarService.uploadAvatar(studentId, avatar);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/{id}/download")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.getAvatarById(id);

        if (avatar == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Avatar not found");
            return;
        }

        Path path = Path.of(avatar.getFilePath());

        if (!Files.exists(path)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            return;
        }

        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(path), 1024);
             BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream(), 1024)) {
            response.setContentType(avatar.getMediaType());
            response.setContentLength(Math.toIntExact(avatar.getFileSize()));
            bis.transferTo(bos);
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while processing the file");
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteAvatar(@PathVariable Long id) {
        avatarService.deleteAvatar(id);
    }

}
