package ru.hogwarts.school.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;
import ru.hogwarts.school.util.SoutUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
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
        Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
        logger.info("Был вызван метод: getStudents");
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
        Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
        logger.info("Был вызван метод: getCountOfStudents");
        return studentRepository.getCountOfStudents();
    }

    @Override
    public Double getAverageAgeOfStudents() {
        Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
        logger.info("Был вызван метод: getAverageAgeOfStudents");
        return studentRepository.getAverageAgeOfStudents();
    }

    @Override
    public Double getAverageAgeOfStudentsWithStreamApi() {
        Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
        logger.info("Был вызван метод: getAverageAgeOfStudentsWithStreamApi");
        return studentRepository.findAll().stream()
                .map(Student::getAge)
                .mapToInt(age -> age)
                .average().orElse(Double.NaN);
    }

    @Override
    public void getSixStudentsByParallel() {
        Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
        logger.info("Был вызван метод: getSixStudentsByParallel");
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
        Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
        logger.info("Был вызван метод: getSixStudentsByParallelWithSynchronized");
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
        Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
        logger.info("Был вызван метод: getLastFiveStudents");
        return studentRepository.getLastFiveStudents();
    }

    @Override
    public List<Student> findByNameIgnoreCase(String name) {
        Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
        logger.info("Был вызван метод: findByNameIgnoreCase");
        List<Student> studentsByName = studentRepository.findByNameIgnoreCase(name);
        if (!studentsByName.isEmpty()) {
            return studentsByName;
        }
        throw new EntityNotFoundException("Ошибка! Студентов с данным именем не найдено.");
    }

    @Override
    public List<Student> getStudentsByAge(Integer age) {
        Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
        logger.info("Был вызван метод: getStudentsByAge");
        List<Student> studentsByAge = studentRepository.findStudentsByAge(age);
        if (!studentsByAge.isEmpty()) {
            return studentsByAge;
        }
        throw new EntityNotFoundException("Ошибка! Студентов с данным возрастом не найдено.");
    }

    @Override
    public List<Student> findByAgeBetween(Integer min, Integer max) {
        Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
        logger.info("Был вызван метод: findByAgeBetween");
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Student getStudentById(Long id) {
        Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
        logger.info("Был вызван метод: getStudentById");
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new EntityNotFoundException("Ошибка! Студента с данным id не найдено");
        }
        return student.get();
    }

    @Override
    public Faculty getFacultyOfStudent(Student student) {
        Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
        logger.info("Был вызван метод: getFacultyOfStudent");
        student = studentRepository.findById(student.getId()).orElseThrow(() -> new EntityNotFoundException("Ошибка! Студента с данным id не найдено"));
        return student.getFaculty();
    }

    @Transactional
    @Override
    public Student addStudent(Student student) {
        Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
        logger.info("Был вызван метод: addStudent");
        return studentRepository.save(student);
    }

    @Transactional
    @Override
    public Student updateStudent(Long id, Student changedStudent) {
        Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
        logger.info("Был вызван метод: updateStudent");
        Student student = getStudentById(id);
        student.setName(changedStudent.getName());
        student.setAge(changedStudent.getAge());
        return addStudent(student);
    }

    @Transactional
    @Override
    public void deleteStudent(Long id) {
        Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
        logger.info("Был вызван метод: deleteStudent");
        studentRepository.delete(getStudentById(id));
    }



}
