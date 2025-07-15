package am.itspace.task.manager.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ErrorMessage {

  private String message;
  private int code;
  private HttpStatus httpStatus;
  private LocalDateTime timestamp;

  public ErrorMessage(String message, int code, HttpStatus httpStatus, LocalDateTime timestamp) {
    this.message = message;
    this.code = code;
    this.httpStatus = httpStatus;
    this.timestamp = timestamp;
  }

}
