package ru.hogwarts.school.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.exception.FacultySaveException;
import ru.hogwarts.school.exception.FacultyUpdateException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final StudentService studentService;

    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentService studentService) {
        this.facultyRepository = facultyRepository;
        this.studentService = studentService;
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
    @Override
    public void deleteFaculty(Long id) {
        facultyRepository.delete(getFacultiesById(id));
    }

    @Override
    public Faculty addStudentToFacultyById(Long studentIdNumber, String facultyName) {
        Faculty faculty = facultyRepository.getByNameIgnoreCase(facultyName).orElseThrow(() -> new EntityNotFoundException("Ошибка! Факультета с данным названием не существует"));
        Student student = studentService.getStudentsByStudentIdNumber(studentIdNumber);
        faculty.getStudents().add(student);
        return facultyRepository.save(faculty);
    }

    @Override
    public Long getNumberOfFaculties() {
        return facultyRepository.count();
    }
}