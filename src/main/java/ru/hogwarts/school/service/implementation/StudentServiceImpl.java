package ru.hogwarts.school.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.config.annotation.LogNameOfRunningMethod;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;
import ru.hogwarts.school.util.SoutUtil;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@LogNameOfRunningMethod
public class StudentServiceImpl implements StudentService {

    private final FacultyRepository facultyRepository;

    public StudentServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    private final Random random = new Random();
    private final SoutUtil soutUtil = new SoutUtil();

    private final StudentRepository studentRepository;

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public List<String> getStudentsWhoseNameStartsWith(String startWith) {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(student -> student.startsWith(startWith))
                .sorted(String::compareTo)
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    @Override
    public Integer getCountOfStudents() {
        return studentRepository.getCountOfStudents();
    }

    @Override
    public Double getAverageAgeOfStudents() {
        return studentRepository.getAverageAgeOfStudents();
    }

    @Override
    public Double getAverageAgeOfStudentsWithStreamApi() {
        return studentRepository.findAll().stream()
                .map(Student::getAge)
                .mapToInt(age -> age)
                .average().orElse(Double.NaN);
    }

    @Override
    public void getSixStudentsByParallel() {
        List<Student> sixStudents = studentRepository.findAll();

        System.out.println(sixStudents.get(0).getName());
        System.out.println(sixStudents.get(1).getName());

        new Thread(() -> {
            System.out.println(sixStudents.get(2).getName());
            System.out.println(sixStudents.get(3).getName());
        }).start();

        new Thread(() -> {
            System.out.println(sixStudents.get(4).getName());
            System.out.println(sixStudents.get(5).getName());
        }).start();
    }

    @Override
    public void getSixStudentsByParallelWithSynchronized() {
        List<Student> students = studentRepository.findAll();

        System.out.println(students.get(0).getName());
        System.out.println(students.get(1).getName());

        new Thread(() -> {
            soutUtil.printer(students.get(2).getName());
            soutUtil.printer(students.get(3).getName());
        }).start();

        new Thread(() -> {
            soutUtil.printer(students.get(4).getName());
            soutUtil.printer(students.get(5).getName());
        }).start();
    }

    @Override
    public List<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
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
