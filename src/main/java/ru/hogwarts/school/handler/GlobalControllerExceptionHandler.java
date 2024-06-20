package ru.hogwarts.school.handler;

import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.hogwarts.school.exception.FacultySaveException;
import ru.hogwarts.school.exception.FacultyUpdateException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity illegalArgument(@NotNull IllegalArgumentException exception) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = FacultySaveException.class)
    public ResponseEntity facultySaveException(@NotNull FacultySaveException exception) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = FacultyUpdateException.class)
    public ResponseEntity facultyUpdateException(@NotNull FacultyUpdateException exception) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity entityNotFoundException(@NotNull EntityNotFoundException exception) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

}
