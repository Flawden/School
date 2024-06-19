package ru.hogwarts.school.service.implementation;

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

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getStudentsByName(String name) {
        List<Student> studentsByName = studentRepository.findStudentsByName(name);
        if (!studentsByName.isEmpty()) {
            return studentsByName;
        }
        throw new IllegalArgumentException("Ошибка! Студентов с данным именем не найдено.");
    }

    public List<Student> getStudentsByAge(Integer age) {
        List<Student> studentsByAge = studentRepository.findStudentsByAge(age);
        if (!studentsByAge.isEmpty()) {
            return studentsByAge;
        }
        throw new IllegalArgumentException("Ошибка! Студентов с данным возрастом не найдено.");
    }

    public Student getStudentsById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new IllegalArgumentException("Ошибка! Студента с данным id не найдено");
        }
        return student.get();
    }

    @Transactional
    public Student addStudent(String name, Integer age) {
        return studentRepository.save(new Student(name, age));
    }

    public Student updateStudent(Long id, Student changedStudent) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ошибка! Студента с данным id не найдено."));
        student.setName(changedStudent.getName());
        student.setAge(changedStudent.getAge());
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.delete(getStudentsById(id));
    }


}
