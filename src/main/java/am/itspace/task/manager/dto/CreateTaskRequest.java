package am.itspace.task.manager.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTaskRequest {

  @NotNull(message = "Title should not be null")
  @NotEmpty(message = "Title is required")
  private String title;

  @NotNull(message = "Description should not be null")
  @NotEmpty(message = "Description is required")
  private String description;

  @NotNull(message = "Completed should not be null")
  private Boolean isCompleted;
}
