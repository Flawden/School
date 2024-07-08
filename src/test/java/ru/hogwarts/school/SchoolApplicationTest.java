package ru.hogwarts.school;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:applicationTest.properties")
public class SchoolApplicationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private FacultyController facultyController;

	@Autowired
	private StudentController studentController;

	@Test
	public void contextLoads() {
		Assertions.assertNotNull(facultyController);
		Assertions.assertNotNull(studentController);
	}

}
