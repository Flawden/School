package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    public StudentService() {
        students = new HashMap<>();
        students.put(0L, new Student(0L, "Валера", 20));
        students.put(1L, new Student(1L, "Сережа", 15));
        students.put(2L, new Student(2L, "Вова", 14));
        students.put(3L, new Student(3L, "Стас", 20));
        students.put(4L, new Student(4L, "Боб", 43));
        students.put(5L, new Student(5L, "Авраам", 56));
    }

    private static HashMap<Long, Student> students;

    public HashMap<Long, Student> getStudents() {
        return students;
    }

    public List<Student> getStudentsByName(String name) {
        ArrayList<Student> studentsByName = new ArrayList<>();
        for (Student student: students.values()) {
            if (student.getName().equals(name)) {
                studentsByName.add(student);
            }
        }
        if (studentsByName.size() > 0) {
            return studentsByName;
        }
        throw new IllegalArgumentException("Ошибка! Факультета с данным названием не существует");
    }

    public List<Student> getStudentsByAge(Integer age) {
        ArrayList<Student> studentsByAge = new ArrayList<>();
        for (Student student: students.values()) {
            if (student.getAge().equals(age)) {
                studentsByAge.add(student);
            }
        }
        if (studentsByAge.size() > 0) {
            return studentsByAge;
        }
        throw new IllegalArgumentException("Ошибка! Факультета с данным названием не существует");
    }

    public Student getStudentsById(Long id) {
        Optional<Student> student = Optional.ofNullable(students.get(id));
        if (student.isEmpty()) {
            throw new IllegalArgumentException("Ошибка! Факультета с данным id не существует");
        }
        return student.get();
    }

    public Student addStudents(String name, Integer age) {
        Student student = new Student(students.size() + 1L, name, age);
        checkStudentToDuplicate(student);
        students.put(students.size() + 1L, student);
        return students.get((long) students.size());
    }

    private void checkStudentToDuplicate(Student student) {
        for (Student ExistingStudent: students.values()) {
            if (ExistingStudent.getName().equals(student.getName())) {
                throw new IllegalArgumentException("Ошибка! Факультет с данным названием уже существует!");
            }
        }
    }

    public Student updateStudent(Long id, String name, Integer age) {
        Student student = new Student(id, name, age);
        students.put(id, student);
        return students.get(id);
    }

    public void deleteStudent(Long id) {
        students.remove(id);
    }


}
