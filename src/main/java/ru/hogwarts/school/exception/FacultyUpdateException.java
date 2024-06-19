package ru.hogwarts.school.exception;

public class FacultyUpdateException extends RuntimeException {

    public FacultyUpdateException() {
    }

    public FacultyUpdateException(String message) {
        super(message);
    }
}
