package ru.hogwarts.school.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

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
    public Student getStudentsByStudentIdNumber(Long studentIdNumber) {
        Optional<Student> student = studentRepository.findStudentByStudentIdNumber(studentIdNumber);
        if (student.isEmpty()) {
            throw new EntityNotFoundException("Ошибка! Студента с данным номером студенческого не найдено");
        }
        return student.get();
    }

    @Override
    public Student getStudentsById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new EntityNotFoundException("Ошибка! Студента с данным id не найдено");
        }
        return student.get();
    }

    @Transactional
    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Transactional
    @Override
    public Student updateStudent(Long id, Student changedStudent) {
        Student student = getStudentsById(id);
        student.setName(changedStudent.getName());
        student.setAge(changedStudent.getAge());
        return addStudent(student);
    }

    @Transactional
    @Override
    public void deleteStudent(Long id) {
        studentRepository.delete(getStudentsById(id));
    }


}
