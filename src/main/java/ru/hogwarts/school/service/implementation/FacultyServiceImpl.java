package ru.hogwarts.school.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.annotation.LogNameOfRunningMethod;
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

@LogNameOfRunningMethod
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
        return facultyRepository.findAll();
    }

    @Override
    public Faculty getFacultyById(Long id) {
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
        return getByNameIgnoreCase(faculty.getName()).getStudents();
    }

    @Override
    public Faculty getByNameIgnoreCase(String name) {
        Optional<Faculty> faculty = facultyRepository.getByNameIgnoreCase(name);
        if (faculty.isPresent()) {
            return faculty.get();
        }
        throw new EntityNotFoundException("Ошибка! Факультета с данным названием не существует");
    }

    @Override
    public Faculty getByColorIgnoreCase(String color) {
        Optional<Faculty> faculty = facultyRepository.getByColorIgnoreCase(color);
        if (faculty.isPresent()) {
            return faculty.get();
        }
        throw new EntityNotFoundException("Ошибка! Факультета с данным цветом не существует");
    }

    @Override
    public Faculty getFacultiesById(Long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new EntityNotFoundException("Ошибка! Факультета с данным id не существует");
        }
        return faculty.get();
    }

    @Transactional
    @Override
    public Faculty addFaculty(Faculty faculty) {
        try {
            return facultyRepository.save(faculty);
        } catch (DataIntegrityViolationException e) {
            throw new FacultySaveException("Ошибка! Факультет с переданными именем или цветом уже существуют");
        }
    }


    @Transactional
    @Override
    public Faculty updateFaculty(String name, Faculty changedFaculty) {
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
        facultyRepository.deleteById(id);
    }

    @Override
    public Faculty addStudentToFacultyById(Long id, String facultyName) {
        Faculty faculty = facultyRepository.getByNameIgnoreCase(facultyName).orElseThrow(() -> new EntityNotFoundException("Ошибка! Факультета с данным названием не существует"));
        Student student = studentRepository.findById(id).get();
        faculty.getStudents().add(student);
        return facultyRepository.save(faculty);
    }

    @Override
    public Long getNumberOfFaculties() {
        return facultyRepository.count();
    }
}