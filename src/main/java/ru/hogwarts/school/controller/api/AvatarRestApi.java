package ru.hogwarts.school.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;

public interface AvatarRestApi {

    @Operation(
            summary = "Получить аватар",
            description = "Позволяет получить аватар из базы данных по ID"
    )
    Avatar getAvatarFromDB(Long avatarId);

    @Operation(
            summary = "Получить аватары",
            description = "Позволяет получить все аватары (Работает с пагинацией)"
    )
    ResponseEntity<?> getAvatarsFromDBWithPagination(Integer numberOfPage, Integer sizeOfPage, HttpServletResponse response) throws IOException;

    @Operation(
            summary = "Загрузить аватар",
            description = "Позволяет загрузить аватар"
    )
    ResponseEntity<String> uploadAvatar(Long studentId, MultipartFile avatar) throws IOException;

    @Operation(
            summary = "Скачать аватар",
            description = "Позволяет скачать аватар"
    )
    void downloadAvatar(Long id, HttpServletResponse response) throws IOException;

    @Operation(
            summary = "Удалить аватар",
            description = "Позволяет удалить аватар"
    )
    void deleteAvatar(Long id);

}
