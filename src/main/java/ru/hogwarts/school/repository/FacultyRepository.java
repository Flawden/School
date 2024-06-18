package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;

import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Optional<Faculty> getFacultyByName(String name);
    Optional<Faculty> getFacultyByColor(String color);
    Optional<Faculty> getFacultyById(Long id);
    void deleteById(Long id);

}
