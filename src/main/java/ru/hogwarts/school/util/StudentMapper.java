package ru.hogwarts.school.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.dto.StudentWithFacultyDTO;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

@Component
public class StudentMapper {

    private final ModelMapper mapper;

    public StudentMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Student studentWithFacultyToStudent(StudentWithFacultyDTO studentWithFacultyDTO) {
        return new Student(null, studentWithFacultyDTO.getName(), studentWithFacultyDTO.getAge(), studentWithFacultyDTO.getStudentIdNumber(), mapper.map(studentWithFacultyDTO.getFaculty(), Faculty.class));
    }

    public StudentWithFacultyDTO studentToStudentWithFaculty(Student student) {
        return new StudentWithFacultyDTO(student.getName(), student.getAge(), student.getStudentIdNumber(), mapper.map(student.getFaculty(), FacultyDTO.class));
    }
}
