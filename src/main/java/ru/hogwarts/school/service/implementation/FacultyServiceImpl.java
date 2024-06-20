package ru.hogwarts.school.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultySaveException;
import ru.hogwarts.school.exception.FacultyUpdateException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public List<Faculty> getFaculties() {
        return facultyRepository.findAll();
    }

    public Faculty getByNameIgnoreCase(String name) {
        Optional<Faculty> faculty = facultyRepository.getByNameIgnoreCase(name);
            if (faculty.isPresent()) {
                return faculty.get();
            }
        throw new EntityNotFoundException("Ошибка! Факультета с данным названием не существует");
    }

    public Faculty getByColorIgnoreCase(String color) {
        Optional<Faculty> faculty = facultyRepository.getByColorIgnoreCase(color);
            if (faculty.isPresent()) {
                return faculty.get();
            }

        throw new EntityNotFoundException("Ошибка! Факультета с данным цветом не существует");
    }

    public Faculty getFacultiesById(Long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new EntityNotFoundException("Ошибка! Факультета с данным id не существует");
        }
        return faculty.get();
    }

    @Transactional
    public Faculty addFaculty(Faculty faculty) {
        try {
            return facultyRepository.save(faculty);
        } catch (DataIntegrityViolationException e) {
            throw new FacultySaveException("Ошибка! Факультет с переданными именем или цветом уже существуют");
        }
    }

    @Transactional
    public Faculty updateFaculty(Long id, Faculty changedFaculty) {
        Faculty faculty = getFacultiesById(id);
        faculty.setName(changedFaculty.getName());
        faculty.setColor(changedFaculty.getColor());
        try {
            return addFaculty(faculty);
        } catch (DataIntegrityViolationException e) {
            throw new FacultyUpdateException("Ошибка! Факультет с переданными именем или цветом уже существуют");
        }

    }

    @Transactional
    public void deleteFaculty(Long id) {
        facultyRepository.delete(getFacultiesById(id));
    }

}