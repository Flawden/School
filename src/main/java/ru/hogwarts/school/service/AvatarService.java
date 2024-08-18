package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarService {

    Avatar getAvatarById(Long id);

    List<Avatar> getAvatarsFromDBWithPagination(Integer numberOfPage, Integer sizeOfPage);

    void uploadAvatar(Long studentId, MultipartFile avatar) throws IOException;

    void deleteAvatar(Long id);

}
