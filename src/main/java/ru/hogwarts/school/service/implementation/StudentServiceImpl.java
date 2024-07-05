package ru.hogwarts.school.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class StudentServiceImpl implements StudentService {

    private final FacultyRepository facultyRepository;
    public StudentServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    private final Random random = new Random();

    private final StudentRepository studentRepository;

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }



    @Override
    public List<Student> findByNameIgnoreCase(String name) {
        List<Student> studentsByName = studentRepository.findByNameIgnoreCase(name);
        if (!studentsByName.isEmpty()) {
            return studentsByName;
        }
        throw new EntityNotFoundException("Ошибка! Студентов с данным именем не найдено.");
    }

    @Override
    public List<Student> getStudentsByAge(Integer age) {
        List<Student> studentsByAge = studentRepository.findStudentsByAge(age);
        if (!studentsByAge.isEmpty()) {
            return studentsByAge;
        }
        throw new EntityNotFoundException("Ошибка! Студентов с данным возрастом не найдено.");
    }

    @Override
    public List<Student> findByAgeBetween(Integer min, Integer max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Student getStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new EntityNotFoundException("Ошибка! Студента с данным id не найдено");
        }
        return student.get();
    }

    @Override
    public Faculty getFacultyOfStudent(Student student) {
        student = studentRepository.findById(student.getId()).orElseThrow(() -> new EntityNotFoundException("Ошибка! Студента с данным id не найдено"));
        return student.getFaculty();
    }

    @Transactional
    @Override
    public Student addStudent(Student student) {
        Long facultyCount = facultyRepository.count();
//        if (facultyCount <= 0) {
//            throw new RuntimeException("Ошибка! Невозможно создать студента. Факультетов не найдено.");
//        } else if (facultyCount == 1) {
//            student.setFaculty(facultyRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("Ошибка! Факультет с данным id не найден")));
//        } else {
//            student.setFaculty(facultyRepository.findById(random.nextLong(1, facultyCount)).orElseThrow(() -> new RuntimeException("Ошибка и все тут")));
//        }
        return studentRepository.save(student);
    }

    @Transactional
    @Override
    public Student updateStudent(Long id, Student changedStudent) {
        Student student = getStudentById(id);
        student.setName(changedStudent.getName());
        student.setAge(changedStudent.getAge());
        return addStudent(student);
    }

    @Transactional
    @Override
    public void deleteStudent(Long id) {
        studentRepository.delete(getStudentById(id));
    }



}
