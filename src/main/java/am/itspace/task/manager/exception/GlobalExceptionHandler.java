package am.itspace.task.manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });


    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  @ExceptionHandler({
      TaskNotFoundException.class
  })
  public ResponseEntity<ErrorMessage> handleException(Exception exception) {
    return switch (exception) {
      case TaskNotFoundException ex -> {
        ErrorMessage errorMessage = new ErrorMessage(
            ex.getMessage(),
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND,
            LocalDateTime.now()
        );
        yield ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
      }
      default -> {
        ErrorMessage error = new ErrorMessage(
            "An unexpected error occurred. Please contact support.",
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            HttpStatus.INTERNAL_SERVER_ERROR,
            LocalDateTime.now()
        );
        yield ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
      }
    };
  }


}
