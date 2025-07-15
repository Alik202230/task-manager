package am.itspace.task.manager.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
public class CreateTaskResponse {
  private Long id;
  private String title;
  private String description;
  private Boolean isCompleted;
  private LocalDateTime createdAt;

  public CreateTaskResponse(Long id, String title, String description, Boolean isCompleted, LocalDateTime createdAt) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.isCompleted = isCompleted;
    this.createdAt = createdAt;
  }

}
