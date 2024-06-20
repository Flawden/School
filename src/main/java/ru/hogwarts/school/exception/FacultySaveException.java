package ru.hogwarts.school.exception;

public class FacultySaveException extends RuntimeException {

    public FacultySaveException() {
    }

    public FacultySaveException(String message) {
        super(message);
    }
}
