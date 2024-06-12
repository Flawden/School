package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

@ExtendWith(MockitoExtension.class)
public class FacultyControllerTest {

    @Mock
    private FacultyService facultyService;

    private FacultyController facultyController;

    @BeforeEach
    public void setUp() {
        facultyController = new FacultyController(facultyService);
    }

}
