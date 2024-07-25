package ru.hogwarts.school.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultySaveException;
import ru.hogwarts.school.exception.FacultyUpdateException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Faculty> getFaculties() {
        log.info("Был вызван метод: getFaculties");
        return facultyRepository.findAll();
    }

    @Override
    public Faculty getFacultyById(Long id) {
        log.info("Был вызван метод: getFacultyById");
        return facultyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ошибка! Факультета с данным id не найдено"));
    }

    @Override
    public String getTheLongestNameOfFaculty() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElseThrow(() -> new NullPointerException("Ошибка! Студентов не найдено!"));
    }

    @Override
    public List<Student> getStudentsOfFaculty(Faculty faculty) {
        log.info("Был вызван метод: getStudentsOfFaculty");
        return getByNameIgnoreCase(faculty.getName()).getStudents();
    }

    @Override
    public Faculty getByNameIgnoreCase(String name) {
        log.info("Был вызван метод: getByNameIgnoreCase");
        Optional<Faculty> faculty = facultyRepository.getByNameIgnoreCase(name);
        if (faculty.isPresent()) {
            return faculty.get();
        }
        throw new EntityNotFoundException("Ошибка! Факультета с данным названием не существует");
    }

    @Override
    public Faculty getByColorIgnoreCase(String color) {
        log.info("Был вызван метод: getByColorIgnoreCase");
        Optional<Faculty> faculty = facultyRepository.getByColorIgnoreCase(color);
        if (faculty.isPresent()) {
            return faculty.get();
        }
        throw new EntityNotFoundException("Ошибка! Факультета с данным цветом не существует");
    }

    @Override
    public Faculty getFacultiesById(Long id) {
        log.info("Был вызван метод: getFacultiesById");
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new EntityNotFoundException("Ошибка! Факультета с данным id не существует");
        }
        return faculty.get();
    }

    @Transactional
    @Override
    public Faculty addFaculty(Faculty faculty) {
        log.info("Был вызван метод: addFaculty");
        try {
            return facultyRepository.save(faculty);
        } catch (DataIntegrityViolationException e) {
            throw new FacultySaveException("Ошибка! Факультет с переданными именем или цветом уже существуют");
        }
    }


    @Transactional
    @Override
    public Faculty updateFaculty(String name, Faculty changedFaculty) {
        log.info("Был вызван метод: updateFaculty");
        Faculty faculty = getByNameIgnoreCase(name);
        faculty.setName(changedFaculty.getName());
        faculty.setColor(changedFaculty.getColor());
        try {
            return addFaculty(faculty);
        } catch (DataIntegrityViolationException e) {
            throw new FacultyUpdateException("Ошибка! Факультет с переданными именем или цветом уже существуют");
        }

    }

    @Transactional
    @Override
    public void deleteFaculty(Long id) {
        log.info("Был вызван метод: deleteFaculty");
        facultyRepository.deleteById(id);
    }

    @Override
    public Faculty addStudentToFacultyById(Long id, String facultyName) {
        log.info("Был вызван метод: addStudentToFacultyById");
        Faculty faculty = facultyRepository.getByNameIgnoreCase(facultyName).orElseThrow(() -> new EntityNotFoundException("Ошибка! Факультета с данным названием не существует"));
        Student student = studentRepository.findById(id).get();
        faculty.getStudents().add(student);
        return facultyRepository.save(faculty);
    }

    @Override
    public Long getNumberOfFaculties() {
        log.info("Был вызван метод: getNumberOfFaculties");
        return facultyRepository.count();
    }
}