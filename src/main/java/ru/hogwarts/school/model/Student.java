package ru.hogwarts.school.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer age;
    @Column(unique = true, nullable = false)
    private Long studentIdNumber;

    @ManyToOne
    @JoinColumn(name = "faculty_id", referencedColumnName = "id")
    private Faculty faculty;

    @OneToOne
    private Avatar avatar;
    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Student(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Student(String name, Integer age, Long studentIdNumber) {
        this.name = name;
        this.age = age;
        this.studentIdNumber = studentIdNumber;
    }

    public Student(Long id, String name, Integer age, Faculty faculty, Long studentIdNumber) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.faculty = faculty;
        this.studentIdNumber = studentIdNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(name, student.name) && Objects.equals(age, student.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
