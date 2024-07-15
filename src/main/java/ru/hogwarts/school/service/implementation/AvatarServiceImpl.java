package ru.hogwarts.school.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;
    private final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    @Value("${upload.path}")
    private String uploadPath;

    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    @Override
    public Avatar getAvatarById(Long id) {
        logger.info("Был вызван метод: getAvatarById");
        return avatarRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ошибка! Аватара с данным id не найдено."));
    }

    @Override
    public List<Avatar> getAvatarsFromDBWithPagination(Integer numberOfPage, Integer sizeOfPage) {
        logger.info("Был вызван метод: getAvatarsFromDBWithPagination");
        PageRequest paging = PageRequest.of(numberOfPage, sizeOfPage);
        Page<Avatar> pagedResult = avatarRepository.findAll(paging);
        return pagedResult.getContent();
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        logger.info("Был вызван метод: uploadAvatar");
        Student student = studentService.getStudentById(studentId);
        byte[] bytes = null;
        Path filePath = Path.of(uploadPath, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (BufferedInputStream bis = new BufferedInputStream(file.getInputStream(), 1024);
             BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(filePath, CREATE_NEW));
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = findAvatarIdByStudentId(studentId);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());
        avatar.setStudent(student);
        avatarRepository.save(avatar);
    }

    public Avatar findAvatarIdByStudentId(Long studentId) {
        logger.info("Был вызван метод: findAvatarIdByStudentId");
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    @Override
    public void deleteAvatar(Long id) {
        logger.info("Был вызван метод: deleteAvatar");
        avatarRepository.deleteById(id);
    }

    private String getExtension(String fileName) {
        logger.info("Был вызван метод: getExtension");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}