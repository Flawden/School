SELECT student.name, student.age, faculty.name, faculty.color FROM student LEFT JOIN faculty ON student.faculty_id = faculty.id;
SELECT * FROM student INNER JOIN avatar ON student.id = avatar.student_id;